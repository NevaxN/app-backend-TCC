package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.mappers.TrabalhoEventoMapper;
import com.app.src.models.TrabalhoEvento;
import com.app.src.repositories.TrabalhoEventoRepository;

@Service
public class TrabalhoEventoService extends 
GenericCrudService<TrabalhoEvento, TrabalhoEventoDTO, Integer, TrabalhoEventoRepository>{

    public TrabalhoEventoService(TrabalhoEventoRepository repository, TrabalhoEventoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "trabalhos-evento", key = "#id")
    public TrabalhoEventoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<TrabalhoEventoDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<TrabalhoEvento> formacoes = repository.findByPesquisadorId(idPesquisador);
        return formacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public TrabalhoEventoDTO atualizar(Integer id, TrabalhoEventoDTO trabalhoEventoDTO){
        TrabalhoEvento existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trabalho de evento não encontrado com id: " + id));
        
        ((TrabalhoEventoMapper) mapper).updateEntityFromDto(trabalhoEventoDTO, existente);

        TrabalhoEvento salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }
}
