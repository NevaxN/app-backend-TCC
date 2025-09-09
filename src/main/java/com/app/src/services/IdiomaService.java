package com.app.src.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.src.models.Idioma;
import com.app.src.models.Pesquisador;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IdiomaService {

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    
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
