package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.models.FormacaoAcademica;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.ArtigoDTO;
import com.app.src.mappers.ArtigoMapper;
import com.app.src.models.Artigo;
import com.app.src.repositories.ArtigoRepository;

@Service
public class ArtigoService extends GenericCrudService<Artigo, ArtigoDTO, Integer, ArtigoRepository> {

    public ArtigoService(ArtigoRepository repository, ArtigoMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "artigos", key = "#id")
    public ArtigoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<ArtigoDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<Artigo> artigos = repository.findByPesquisadorId(idPesquisador);
        return artigos.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ArtigoDTO atualizar(Integer id, ArtigoDTO dadosAtualizados){
        Artigo existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Artigo não encontrado com id: " + id));

        ((ArtigoMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Artigo salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }
}
