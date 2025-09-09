package com.app.src.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.src.models.Orientacao;
import com.app.src.models.Pesquisador;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrientacaoService {

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;
    
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
}
