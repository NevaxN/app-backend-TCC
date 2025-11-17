package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.OrientacaoDTO;
import com.app.src.mappers.OrientacaoMapper;
import com.app.src.models.Orientacao;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.OrientacaoRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrientacaoService extends GenericCrudService<Orientacao, OrientacaoDTO, Integer, OrientacaoRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    public OrientacaoService(OrientacaoRepository repository, OrientacaoMapper mapper){
        super(repository, mapper);
    }
    
    @Override
    @Cacheable(value = "orientacoes", key = "#id")
    public OrientacaoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<OrientacaoDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<Orientacao> orientacoes = repository.findByPesquisadorId(idPesquisador);
        return orientacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrientacaoDTO salvar(OrientacaoDTO orientacaoDTO){
        Orientacao orientacao = mapper.toEntity(orientacaoDTO);

        if (orientacao.getPesquisador() == null || orientacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(orientacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + orientacao.getPesquisador().getId());
        }

        return super.salvar(orientacaoDTO);
    }

    public OrientacaoDTO atualizar(Integer id, OrientacaoDTO orientacaoDTO){
        Orientacao orientacao = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orientação não encontrada com id: " + id));

        ((OrientacaoMapper) mapper).updateEntityFromDto(orientacaoDTO, orientacao);

        Orientacao salvo = repository.save(orientacao);

        return mapper.toDTO(salvo);
    }

    public List<Orientacao> converterJsonParaOrientacao (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Orientacao> orientacoesList = new ArrayList<>();

            JsonNode root = objectMapper.readTree(jsonBody);
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
