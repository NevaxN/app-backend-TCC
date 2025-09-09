package com.app.src.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.src.models.Endereco;
import com.app.src.models.Pesquisador;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EnderecoService {

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    
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
