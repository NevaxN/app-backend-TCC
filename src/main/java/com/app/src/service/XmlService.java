package com.app.src.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.app.src.model.*;
import com.app.src.repository.*;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlService {

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    private final ArtigoRepository artigoRepository;
    private final TrabalhoEventoRepository trabalhoEventoRepository;
    private final LivroRepository livroRepository;
    private final CapituloRepository capituloRepository;
    private final UsuarioRepository usuarioRepository;
    private final PesquisadorRepository pesquisadorRepository;
    private final EnderecoRepository enderecoRepository;
    private final IdiomaRepository idiomaRepository;
    private final PremiacaoRepository premiacaoRepository;
    private final FormacaoAcademicaRepository formacaoAcademicaRepository;
    private final AtuacaoProfissionalRepository atuacaoProfissionalRepository;
    private final OrientacaoRepository orientacaoRepository;
    private final ProjetoPesquisaRepository projetoPesquisaRepository;


    public XmlService(ArtigoRepository artigoRepository, TrabalhoEventoRepository trabalhoEventoRepository, LivroRepository livroRepository, CapituloRepository capituloRepository, CapituloRepository capituloRepository1, UsuarioRepository usuarioRepository, PesquisadorRepository pesquisadorRepository, EnderecoRepository enderecoRepository, IdiomaRepository idiomaRepository, PremiacaoRepository premiacaoRepository, FormacaoAcademicaRepository formacaoAcademicaRepository, AtuacaoProfissionalRepository atuacaoProfissionalRepository, OrientacaoRepository orientacaoRepository, ProjetoPesquisaRepository projetoPesquisaRepository) {
        this.artigoRepository = artigoRepository;
        this.trabalhoEventoRepository = trabalhoEventoRepository;
        this.livroRepository = livroRepository;
        this.capituloRepository = capituloRepository1;
        this.usuarioRepository = usuarioRepository;
        this.pesquisadorRepository = pesquisadorRepository;
        this.enderecoRepository = enderecoRepository;
        this.idiomaRepository = idiomaRepository;
        this.premiacaoRepository = premiacaoRepository;
        this.formacaoAcademicaRepository = formacaoAcademicaRepository;
        this.atuacaoProfissionalRepository = atuacaoProfissionalRepository;
        this.orientacaoRepository = orientacaoRepository;
        this.projetoPesquisaRepository = projetoPesquisaRepository;
    }

    public String processarXml(MultipartFile xml) {
        try {
            byte[] conteudoBytes = xml.getBytes();
            String encoding = detectEncoding(conteudoBytes);
            String conteudo = new String(conteudoBytes, Charset.forName(encoding));

            // XML -> JSON
            JSONObject jsonObject = XML.toJSONObject(conteudo);
            String jsonString = jsonObject.toString();

            // Envia JSON para Flask
            RestTemplate restTemplate = new RestTemplate();
            String flaskUrl = "http://keyword-extractor:5000/analyze";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("conteudo_xml", jsonString);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> flaskResponse = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

            String flaskJson = flaskResponse.getBody();

            Usuario usuario = usuarioRepository.findById(1).orElseThrow();
            Pesquisador pesquisador = converterJsonParaPesquisador(flaskJson, usuario);
            Pesquisador pesquisadorSalvo = pesquisadorRepository.save(pesquisador);

            enderecoRepository.saveAll(converterJsonParaEndereco(flaskJson, pesquisadorSalvo));
            formacaoAcademicaRepository.saveAll(converterJsonParaFormacaoAcademica(flaskJson, pesquisadorSalvo));
            converterJsonParaAtuacaoProfissional(flaskJson, pesquisadorSalvo);
            converterJsonParaProducaoBibliografica(flaskJson, pesquisadorSalvo);
            orientacaoRepository.saveAll(converterJsonParaOrientacao(flaskJson, pesquisadorSalvo));
            premiacaoRepository.saveAll(converterJsonParaPremiacao(flaskJson, pesquisadorSalvo));
            idiomaRepository.saveAll(converterJsonParaIdioma(flaskJson, pesquisadorSalvo));

            return flaskResponse.getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ///  MÉTODOS AUXILIARES
    public String detectEncoding(byte[] xmlBytes) {
        String header = new String(xmlBytes, 0, Math.min(xmlBytes.length, 100), StandardCharsets.US_ASCII);
        Pattern pattern = Pattern.compile("encoding=[\"'](.*?)[\"']");
        Matcher matcher = pattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "UTF-8"; // fallback padrão
    }

    private <T> T getValue (JsonNode node, String key, T defaultValue, Class<T> type) {
        if (node.has(key) && !node.get(key).isNull()) {
            JsonNode value = node.get(key);
            if (type == String.class) return type.cast(value.asText());
            if (type == Integer.class) return type.cast(value.asInt());
        } else {
            return defaultValue;
        };
        return defaultValue;
    }

    ///  MÉTODOS ESPECÍFICOS PARA CONVERSÃO DOS DADOS DO LATTES

    public Pesquisador converterJsonParaPesquisador(String jsonBody, Usuario usuario) {
        try {

            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador");

            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HHmmss");

            Pesquisador pesquisador = new Pesquisador();
            pesquisador.setUsuario(usuario);
            pesquisador.setLattesId(dados.get("numero_identificador").asLong());

            pesquisador.setDataNascimento(LocalDate.parse(dados.get("data_nascimento").asText(), dataFormatter));
            pesquisador.setDataAtualizacao(LocalDate.parse(dados.get("data_atualizacao").asText(), dataFormatter));
            pesquisador.setHoraAtualizacao(LocalTime.parse(dados.get("hora_atualizacao").asText(), horaFormatter));

            pesquisador.setNomeCitacoesBibliograficas(dados.get("citacoes_bibliograficas").asText());
            pesquisador.setNacionalidade(dados.get("nacionalidade").asText());
            pesquisador.setPaisNascimento(dados.get("pais_nascimento").asText());

            String[] nomePartes = dados.get("nome_completo").asText().split(" ", 2);
            pesquisador.setNomePesquisador(nomePartes[0]);
            pesquisador.setSobrenome(nomePartes.length > 1 ? nomePartes[1] : "");

            return pesquisador;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Pesquisador", e);
        }
    }

    public List<Endereco> converterJsonParaEndereco (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Endereco> enderecoList = new ArrayList<>();
            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("endereco");

            for (JsonNode e: dados) {
                Endereco endereco = new Endereco();

                endereco.setPesquisador(pesquisador);
                endereco.setTipo(getValue(e, "tipo", MISSING_STRING_VALUE, String.class));
                endereco.setPais(getValue(e, "pais", MISSING_STRING_VALUE, String.class));
                endereco.setCidade(getValue(e, "cidade", MISSING_STRING_VALUE, String.class));
                endereco.setBairro(getValue(e, "bairro", MISSING_STRING_VALUE, String.class));
                endereco.setTelefone(getValue(e, "telefone", MISSING_STRING_VALUE, String.class));
                endereco.setEmail(getValue(e, "email", MISSING_STRING_VALUE, String.class));

                enderecoList.add(endereco);
            }
            return enderecoList;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Endereço", e);
        }
    }

    public List<FormacaoAcademica> converterJsonParaFormacaoAcademica (String jsonBody, Pesquisador pesquisador) {
        try {
            List<FormacaoAcademica> formacaoAcademicaList = new ArrayList<>();

            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("formacao_academica");

            for (JsonNode fa: dados) {
                FormacaoAcademica formacaoAcademica = new FormacaoAcademica();

                formacaoAcademica.setPesquisador(pesquisador);
                formacaoAcademica.setSequenciaFormacao(getValue(fa, "sequencia_formacao", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setNivel(getValue(fa, "tipo", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setCurso(getValue(fa, "nome_curso", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setInstituicao(getValue(fa, "nome_instituicao", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setStatus(getValue(fa, "status", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setAnoInicio(getValue(fa, "ano_de_inicio", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setAnoConclusao(getValue(fa, "ano_de_conclusao", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setTituloTrabalho(getValue(fa, "titulo_trabalho", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setOrientador(getValue(fa, "orientador", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setDestaque(false);

                formacaoAcademicaList.add(formacaoAcademica);
            }

            return formacaoAcademicaList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Formação Acadêmica", e);
        }
    }

    public List<Idioma> converterJsonParaIdioma (String jsonBody, Pesquisador pesquisador) {
       try {
           List<Idioma> idiomaList = new ArrayList<>();
           JsonNode root = mapper.readTree(jsonBody);
           JsonNode dados = root.get("dados_pesquisador").get("idiomas");

           for (JsonNode i: dados) {
               Idioma idioma = new Idioma();
               idioma.setPesquisador(pesquisador);
               idioma.setIdioma(getValue(i, "idioma", MISSING_STRING_VALUE, String.class));
               idioma.setEscrita(getValue(i, "escrita", MISSING_STRING_VALUE, String.class));
               idioma.setLeitura(getValue(i, "leitura", MISSING_STRING_VALUE, String.class));
               idioma.setFala(getValue(i, "fala", MISSING_STRING_VALUE, String.class));
               idiomaList.add(idioma);
           }

           return idiomaList;

       } catch (Exception e) {
           throw new RuntimeException("Erro ao converter JSON para Idioma", e);
       }
    }

    public List<Premiacao> converterJsonParaPremiacao (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Premiacao> premiacaoList = new ArrayList<>();
            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("premiacoes");

            for (JsonNode p: dados) {
                Premiacao premiacao = new Premiacao();
                premiacao.setPesquisador(pesquisador);
                premiacao.setTitulo(getValue(p, "titulo", MISSING_STRING_VALUE, String.class));
                premiacao.setInstituicao(getValue(p, "instituicao", MISSING_STRING_VALUE, String.class));
                premiacao.setAno(getValue(p, "ano", MISSING_INTEGER_VALUE, Integer.class));
                premiacaoList.add(premiacao);
            }

            return premiacaoList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Idioma", e);
        }
    }

    public void converterJsonParaAtuacaoProfissional (String jsonBody, Pesquisador pesquisador) {
        try {

            List<AtuacaoProfissional> atuacaoProfissionalList = new ArrayList<>();
            List<ProjetoPesquisa> projetoPesquisaList = new ArrayList<>();

            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("atuacoes_profissionais");

            for (JsonNode ap: dados) {
                String instituicao = getValue(ap, "instituicao", MISSING_STRING_VALUE, String.class);
                Integer sequenciaAtuacao = getValue(ap, "sequencia_atuacao", MISSING_INTEGER_VALUE, Integer.class);

                JsonNode vinculos = ap.get("vinculos");

                if (vinculos != null && vinculos.isArray()) {
                    for (JsonNode v : vinculos) {
                        AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();

                        atuacaoProfissional.setPesquisador(pesquisador);
                        atuacaoProfissional.setInstituicao(instituicao);
                        atuacaoProfissional.setSequenciaAtuacao(sequenciaAtuacao);

                        atuacaoProfissional.setCargo(getValue(v, "cargo", MISSING_STRING_VALUE, String.class));
                        atuacaoProfissional.setSequenciaVinculo(getValue(v, "sequencia_vinculo", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setMesInicio(getValue(v, "mes_inicio", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setMesFim(getValue(v, "mes_fim", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setAnoInicio(getValue(v, "ano_inicio", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setAnoFim(getValue(v, "ano_fim", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setDestaque(false);

                        atuacaoProfissionalList.add(atuacaoProfissional);
                    }
                }

                JsonNode projetosPesquisa = ap.get("projetos_de_pesquisa");
                if (projetosPesquisa != null && projetosPesquisa.isArray()) {
                    for (JsonNode p: projetosPesquisa) {
                        ProjetoPesquisa projetoPesquisa = new ProjetoPesquisa();

                        projetoPesquisa.setPesquisador(pesquisador);
                        projetoPesquisa.setTitulo(getValue(p, "titulo", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setFinanciador(getValue(p, "financiador", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setDescricao(getValue(p, "descricao", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setAno(getValue(p, "ano", MISSING_INTEGER_VALUE, Integer.class));
                        projetoPesquisa.setSequencia(getValue(p, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                        projetoPesquisa.setInstituicao(instituicao);
                        projetoPesquisa.setDestaque(false);

                        projetoPesquisaList.add(projetoPesquisa);
                    }
                }
            }

            atuacaoProfissionalRepository.saveAll(atuacaoProfissionalList);
            projetoPesquisaRepository.saveAll(projetoPesquisaList);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Atuação Profissional", e);
        }
    }

    public List<Orientacao> converterJsonParaOrientacao (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Orientacao> orientacoesList = new ArrayList<>();

            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("orientacoes");

            for (JsonNode o : dados) {
                Orientacao orientacao = new Orientacao();

                orientacao.setPesquisador(pesquisador);
                orientacao.setTipo(getValue(o, "tipo", MISSING_STRING_VALUE, String.class));
                orientacao.setTituloTrabalho(getValue(o, "titulo", MISSING_STRING_VALUE, String.class));
                orientacao.setInstituicao(getValue(o, "instituicao", MISSING_STRING_VALUE, String.class));
                orientacao.setNomeOrientado(getValue(o, "orientado", MISSING_STRING_VALUE, String.class));
                orientacao.setNomeCurso(getValue(o, "curso", MISSING_STRING_VALUE, String.class));
                orientacao.setSequencia(getValue(o, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                orientacao.setAno(getValue(o, "ano", MISSING_INTEGER_VALUE, Integer.class));
                orientacao.setDestaque(false);

                orientacoesList.add(orientacao);
            }

            return orientacoesList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Orientação", e);
        }
    }

    public void converterJsonParaProducaoBibliografica (String jsonBody, Pesquisador pesquisador) {
        try {
            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("producoes_bibliograficas");

            // Eventos
            List<TrabalhoEvento> trabalhoEventosList = new ArrayList<>();
            JsonNode trabalhoEventos = dados.get("eventos");

            for (JsonNode te : trabalhoEventos) {
                TrabalhoEvento trabalhoEvento = new TrabalhoEvento();

                trabalhoEvento.setPesquisador(pesquisador);
                trabalhoEvento.setTitulo(getValue(te, "titulo", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setNomeEvento(getValue(te, "nome_evento", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setCidadeEvento(getValue(te, "cidade", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setClassificacaoEvento(getValue(te, "classificacao", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setAutores(getValue(te, "autores", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setSequenciaProducao(getValue(te, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                trabalhoEvento.setAno(getValue(te, "ano", MISSING_INTEGER_VALUE, Integer.class));
                trabalhoEvento.setDestaque(false);

                trabalhoEventosList.add(trabalhoEvento);
            }

            // Artigos
            List<Artigo> artigosList = new ArrayList<>();
            JsonNode artigos = dados.get("artigos");

            for (JsonNode a : artigos) {
                Artigo artigo = new Artigo();

                artigo.setPesquisador(pesquisador);
                artigo.setDoi(getValue(a, "doi", MISSING_STRING_VALUE, String.class));
                artigo.setTitulo(getValue(a, "titulo", MISSING_STRING_VALUE, String.class));
                artigo.setPeriodico(getValue(a, "periodico", MISSING_STRING_VALUE, String.class));
                artigo.setIdioma(getValue(a, "idioma", MISSING_STRING_VALUE, String.class));
                artigo.setAutores(getValue(a, "autores", MISSING_STRING_VALUE, String.class));
                artigo.setSequenciaProducao(getValue(a, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                artigo.setAno(getValue(a, "ano", MISSING_INTEGER_VALUE, Integer.class));
                artigo.setDestaque(false);

                artigosList.add(artigo);
            }

            // Livros
            List<Livro> livroList = new ArrayList<>();
            JsonNode livros = dados.get("livros");

            for (JsonNode l: livros) {
                Livro livro = new Livro();

                livro.setPesquisador(pesquisador);
                livro.setTitulo(getValue(l, "titulo", MISSING_STRING_VALUE, String.class));
                livro.setEditora(getValue(l, "editora", MISSING_STRING_VALUE, String.class));
                livro.setIdioma(getValue(l, "idioma", MISSING_STRING_VALUE, String.class));
                livro.setIsbn(getValue(l, "isbn", MISSING_STRING_VALUE, String.class));
                livro.setSequenciaProducao(getValue(l, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                livro.setNumeroPaginas(getValue(l, "numero_paginas", MISSING_INTEGER_VALUE, Integer.class));
                livro.setAno(getValue(l, "ano", MISSING_INTEGER_VALUE, Integer.class));
                livro.setAutores(getValue(l, "autores", MISSING_STRING_VALUE, String.class));
                livro.setDestaque(false);

                livroList.add(livro);
            }

            // Capítulos
            List<Capitulo> capituloList = new ArrayList<>();
            JsonNode capitulos = dados.get("capitulos");

            for (JsonNode c: capitulos) {
                Capitulo capitulo = new Capitulo();

                capitulo.setPesquisador(pesquisador);
                capitulo.setTituloCapitulo(getValue(c, "titulo", MISSING_STRING_VALUE, String.class));
                capitulo.setEditora(getValue(c, "editora", MISSING_STRING_VALUE, String.class));
                capitulo.setDoi(getValue(c, "doi", MISSING_STRING_VALUE, String.class));
                capitulo.setIdioma(getValue(c, "idioma", MISSING_STRING_VALUE, String.class));
                capitulo.setNomeLivro(getValue(c, "nome_livro", MISSING_STRING_VALUE, String.class));
                capitulo.setPaginaInicial(getValue(c, "pagina_inicial", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setPaginaFinal(getValue(c, "pagina_final", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setAno(getValue(c, "ano", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setSequenciaProducao(getValue(c, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setAutores(getValue(c, "autores", MISSING_STRING_VALUE, String.class));
                capitulo.setDestaque(false);

                capituloList.add(capitulo);
            }

            trabalhoEventoRepository.saveAll(trabalhoEventosList);
            artigoRepository.saveAll(artigosList);
            livroRepository.saveAll(livroList);
            capituloRepository.saveAll(capituloList);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Produção Bibliográfica", e);
        }
    }

}
