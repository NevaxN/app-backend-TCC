package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.EnderecoDTO;
import com.app.src.mappers.EnderecoMapper;
import com.app.src.models.Endereco;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.EnderecoRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EnderecoService extends GenericCrudService<Endereco, EnderecoDTO, Integer, EnderecoRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";

    public EnderecoService(EnderecoRepository repository, EnderecoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "enderecos", key = "#id")
    public EnderecoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public EnderecoDTO salvar(EnderecoDTO enderecoDTO){
        Endereco endereco = mapper.toEntity(enderecoDTO);
        
        if (endereco.getPesquisador() == null || endereco.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(endereco.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + endereco.getPesquisador().getId());
        }

        return super.salvar(enderecoDTO);
    }

    @Override
    public EnderecoDTO atualizar(Integer id, EnderecoDTO dadosAtualizados){
        Endereco existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Endereco não encontrado com id: " + id));

        ((EnderecoMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Endereco salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }
    
    public List<Endereco> converterJsonParaEndereco (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Endereco> enderecoList = new ArrayList<>();
            JsonNode root = objectMapper.readTree(jsonBody);
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
