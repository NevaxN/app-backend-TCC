package com.app.src.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.app.src.models.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class XmlService {

    final ObjectMapper mapper = new ObjectMapper();

    public String detectEncoding(byte[] xmlBytes) {
        String header = new String(xmlBytes, 0, Math.min(xmlBytes.length, 100), StandardCharsets.US_ASCII);
        Pattern pattern = Pattern.compile("encoding=[\"'](.*?)[\"']");
        Matcher matcher = pattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "UTF-8"; // fallback padrão
    }

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
                endereco.setTipo(e.get("tipo").asText());
                endereco.setPais(e.get("pais").asText());
                endereco.setCidade(e.get("cidade").asText());
                endereco.setBairro(e.get("bairro").asText());
                endereco.setTelefone(e.get("telefone").asText());
                endereco.setEmail(e.get("email").asText());
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
                formacaoAcademica.setSequenciaFormacao(fa.get("sequencia_formacao").asInt());
                formacaoAcademica.setNivel(fa.get("tipo").asText());
                formacaoAcademica.setCurso(fa.get("nome_curso").asText());
                formacaoAcademica.setInstituicao(fa.get("nome_instituicao").asText());
                formacaoAcademica.setStatus(fa.get("status").asText());
                formacaoAcademica.setAnoInicio(fa.get("ano_de_inicio").asInt());
                formacaoAcademica.setAnoConclusao(fa.get("ano_de_conclusao").asInt());
                formacaoAcademica.setTituloTrabalho(fa.get("titulo_trabalho").asText());
                formacaoAcademica.setDestaque(false);


                String orientador = (fa.has("orientador") && !fa.get("orientador").asText().isEmpty())
                        ? fa.get("orientador").asText()
                        : "Não informado";
                formacaoAcademica.setOrientador(orientador);

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
               idioma.setIdioma(i.get("idioma").asText());
               idioma.setEscrita(i.get("escrita").asText());
               idioma.setLeitura(i.get("leitura").asText());
               idioma.setFala(i.get("fala").asText());
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
                premiacao.setTitulo(p.get("titulo").asText());
                premiacao.setInstituicao(p.get("instituicao").asText());
                premiacao.setAno(p.get("ano").asInt());
                premiacaoList.add(premiacao);
            }

            return premiacaoList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Idioma", e);
        }
    }

}
