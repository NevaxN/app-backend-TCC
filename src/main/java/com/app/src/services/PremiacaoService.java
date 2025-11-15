package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.models.TrabalhoEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.PremiacaoDTO;
import com.app.src.mappers.PremiacaoMapper;
import com.app.src.models.Pesquisador;
import com.app.src.models.Premiacao;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.PremiacaoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PremiacaoService extends GenericCrudService<Premiacao, PremiacaoDTO, Integer, PremiacaoRepository>{

    @Autowired
    private PesquisadorRepository pesquisadorRepository;
    
    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    public PremiacaoService(PremiacaoRepository repository, PremiacaoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable
    public PremiacaoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<PremiacaoDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<Premiacao> premiacoes = repository.findByPesquisadorId(idPesquisador);
        return premiacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PremiacaoDTO salvar(PremiacaoDTO premiacaoDTO){
        Premiacao premiacao = mapper.toEntity(premiacaoDTO);

        if (premiacao.getPesquisador() == null || premiacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(premiacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + premiacao.getPesquisador().getId());
        }

        return super.salvar(premiacaoDTO);
    }

    public PremiacaoDTO atualizar(Integer id, PremiacaoDTO premiacaoDTO){
        Premiacao premiacao = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premiacao não encontrado com id: " + id));

        ((PremiacaoMapper) mapper).updateEntityFromDto(premiacaoDTO, premiacao);

        Premiacao salvo = repository.save(premiacao);
    
        return mapper.toDTO(salvo);
    }


    public List<Premiacao> converterJsonParaPremiacao (String jsonBody, Pesquisador pesquisador) {
        try {
            List<Premiacao> premiacaoList = new ArrayList<>();
            JsonNode root = objectMapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("premiacoes");

            for (JsonNode p: dados) {
                Premiacao premiacao = new Premiacao();
                premiacao.setPesquisador(pesquisador);
                premiacao.setTitulo(getValue(p, "titulo", MISSING_STRING_VALUE, String.class));
                premiacao.setInstituicao(getValue(p, "instituicao", MISSING_STRING_VALUE, String.class));
                premiacao.setAno(getValue(p, "ano", MISSING_INTEGER_VALUE, Integer.class));
                premiacao.setDestaque(false);
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
