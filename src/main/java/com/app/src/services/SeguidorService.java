package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.SeguidorDTO;
import com.app.src.mappers.SeguidorMapper;
import com.app.src.models.Seguidor;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.SeguidorRepository;

@Service
public class SeguidorService extends GenericCrudService<Seguidor, SeguidorDTO, Integer, SeguidorRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;
    
    public SeguidorService(SeguidorRepository repository, SeguidorMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "seguidores", key = "#id")
    public SeguidorDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public SeguidorDTO salvar(SeguidorDTO seguidorDTO){
        Seguidor seguidor = mapper.toEntity(seguidorDTO);

        if(seguidor.getPesquisador() == null || seguidor.getPesquisador().getId() == null){
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(seguidor.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + seguidor.getPesquisador().getId());
        }

        return super.salvar(seguidorDTO);
    }
}
