package com.app.src.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.app.src.model.*;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class XmlService {

    final ObjectMapper mapper = new ObjectMapper();
    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    public String detectEncoding(byte[] xmlBytes) {
        String header = new String(xmlBytes, 0, Math.min(xmlBytes.length, 100), StandardCharsets.US_ASCII);
        Pattern pattern = Pattern.compile("encoding=[\"'](.*?)[\"']");
        Matcher matcher = pattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "UTF-8"; // fallback padrão
    }

    // Função génerica para obter o valor de um JSON, retornando valor genérico caso seja null
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

    public List<AtuacaoProfissional> converterJsonParaAtuacaoProfissional (String jsonBody, Pesquisador pesquisador) {
        try {

            List<AtuacaoProfissional> atuacaoProfissionalList = new ArrayList<>();

            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("atuacoes_profissionais");

            for (JsonNode ap: dados) {
                AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();

                atuacaoProfissional.setPesquisador(pesquisador);
                atuacaoProfissional.setInstituicao(getValue(ap, "instituicao", MISSING_STRING_VALUE, String.class));
                atuacaoProfissional.setCargo(getValue(ap, "cargo", MISSING_STRING_VALUE, String.class));
                atuacaoProfissional.setSequenciaAtuacao(getValue(ap, "sequencia_atuacao", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setSequenciaVinculo(getValue(ap, "sequencia_vinculo", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setMesInicio(getValue(ap, "mes_inicio", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setMesFim(getValue(ap, "mes_fim", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setAnoInicio(getValue(ap, "ano_inicio", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setAnoFim(getValue(ap, "ano_fim", MISSING_INTEGER_VALUE, Integer.class));
                atuacaoProfissional.setDestaque(false);

                atuacaoProfissionalList.add(atuacaoProfissional);
            }

            return  atuacaoProfissionalList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Atuação Profissional", e);
        }
    }

}
