package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.src.mappers.GenericMapper;

/**
 * Serviço genérico que implementa as operações básicas de CRUD.
 * @param <E> A Entidade
 * @param <D> O DTO
 * @param <ID> O tipo do ID da Entidade (Integer, Long, etc.)
 * @param <R> O Repositório correspondente
 */
public abstract class GenericCrudService<E, D, ID, R extends JpaRepository<E, ID>> {

    protected final R repository;
    protected final GenericMapper<E, D> mapper;

    public GenericCrudService(R repository, GenericMapper<E, D> mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<D> buscarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public D buscarPorId(ID id){
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Recurso não encontrado com id: " + id));
    }

    public D salvar(D dto){
        E entity = mapper.toEntity(dto);
        E savedEntity =  repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    public D atualizar(ID id, D dto){
        if (!repository.existsById(id)){
            throw new NoSuchElementException("Recurso não encontrado com id: " + id);
        }

        E entity = mapper.toEntity(dto);

        E updateEntity = repository.save(entity);

        return mapper.toDTO(updateEntity);
    }

    public String excluir(ID id){
        if(!repository.existsById(id)){
            throw new NoSuchElementException("Recurso não encontrado com id: " + id);
        }

        repository.deleteById(id);

        return "Recurso com id: " + id + " excluido com sucesso!";
    }
    
}
