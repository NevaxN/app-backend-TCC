package com.app.src.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.src.models.Pesquisador;
import com.app.src.models.Premiacao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PremiacaoService {

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;
    
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
