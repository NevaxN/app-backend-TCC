package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.LivroDTO;
import com.app.src.mappers.LivroMapper;
import com.app.src.models.Livro;
import com.app.src.repositories.LivroRepository;

@Service
public class LivroService extends GenericCrudService<Livro, LivroDTO, Integer, LivroRepository> {
    
    public LivroService(LivroRepository repository, LivroMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "livros", key = "#id")
    public LivroDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<LivroDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<Livro> formacoes = repository.findByPesquisadorId(idPesquisador);
        return formacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO atualizar(Integer id, LivroDTO dadosAtualizados){
        Livro existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Livro não encontrado com id: " + id));

        ((LivroMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Livro salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }
}
