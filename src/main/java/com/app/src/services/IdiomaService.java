package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.IdiomaDTO;
import com.app.src.mappers.IdiomaMapper;
import com.app.src.models.Idioma;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.IdiomaRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IdiomaService extends GenericCrudService<Idioma, IdiomaDTO, Integer, IdiomaRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";

    public IdiomaService(IdiomaRepository repository, IdiomaMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "idiomas", key = "#id")
    public IdiomaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public IdiomaDTO salvar(IdiomaDTO idiomaDTO){
        Idioma idioma = mapper.toEntity(idiomaDTO);
        
        if (idioma.getPesquisador() == null || idioma.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(idioma.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + idioma.getPesquisador().getId());
        }

        return super.salvar(idiomaDTO);
    }

    public IdiomaDTO atualizar(Integer id, IdiomaDTO dadosAtualizados){
        Idioma idioma = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Idioma não encontrado com id: " + id));

        ((IdiomaMapper) mapper).updateEntityFromDto(dadosAtualizados, idioma);

        Idioma salvo = repository.save(idioma);

        return mapper.toDTO(salvo);
    }
    
    public List<Idioma> converterJsonParaIdioma (String jsonBody, Pesquisador pesquisador) {
       try {
           List<Idioma> idiomaList = new ArrayList<>();
           JsonNode root = objectMapper.readTree(jsonBody);
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
