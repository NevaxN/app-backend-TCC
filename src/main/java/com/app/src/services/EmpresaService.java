package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.EmpresaDTO;
import com.app.src.mappers.EmpresaMapper;
import com.app.src.models.Empresa;
import com.app.src.repositories.EmpresaRepository;

@Service
public class EmpresaService extends GenericCrudService<Empresa, EmpresaDTO, Integer, EmpresaRepository> {
    
    public EmpresaService(EmpresaRepository repository, EmpresaMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "empresas", key = "#id")
    public EmpresaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public EmpresaDTO atualizar(Integer id, EmpresaDTO dadosAtualizados){
        Empresa existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        ((EmpresaMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Empresa salvo = repository.save(existente);
        
        return mapper.toDTO(salvo); 
    }
}
