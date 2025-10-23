package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.CapituloDTO;
import com.app.src.mappers.CapituloMapper;
import com.app.src.models.Capitulo;
import com.app.src.repositories.CapituloRepository;

@Service
public class CapituloService extends GenericCrudService<Capitulo, CapituloDTO, Integer, CapituloRepository> {
 
    public CapituloService(CapituloRepository repository, CapituloMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "capitulos", key = "#id")
    public CapituloDTO buscarPorId(Integer Id){
        return super.buscarPorId(Id);
    }

    public List<CapituloDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<Capitulo> formacoes = repository.findByPesquisadorId(idPesquisador);
        return formacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public CapituloDTO atualizar(Integer id, CapituloDTO dadosAtualizados){
        Capitulo existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Capitulo não encontrado com id: " + id));
        
        ((CapituloMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Capitulo salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }

}
