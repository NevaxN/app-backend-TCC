package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.mappers.ProjetoPesquisaMapper;
import com.app.src.models.ProjetoPesquisa;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.ProjetoPesquisaRepository;

@Service
public class ProjetoPesquisaService extends 
GenericCrudService<ProjetoPesquisa, ProjetoPesquisaDTO, Integer, ProjetoPesquisaRepository> {
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;


    public ProjetoPesquisaService(ProjetoPesquisaRepository repository, ProjetoPesquisaMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "projetos_pesquisa", key = "#id")
    public ProjetoPesquisaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    public List<ProjetoPesquisaDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<ProjetoPesquisa> formacoes = repository.findByPesquisadorId(idPesquisador);
        return formacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjetoPesquisaDTO salvar(ProjetoPesquisaDTO projetoPesquisaDTO){
        ProjetoPesquisa projetoPesquisa = mapper.toEntity(projetoPesquisaDTO);
        
        if (projetoPesquisa.getPesquisador() == null || projetoPesquisa.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(projetoPesquisa.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + projetoPesquisa.getPesquisador().getId());
        }

        return super.salvar(projetoPesquisaDTO);
    }

    public ProjetoPesquisaDTO atualizar(Integer id, ProjetoPesquisaDTO projetoPesquisaDTO){
        ProjetoPesquisa projetoPesquisa = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id));

        ((ProjetoPesquisaMapper) mapper).updateEntityFromDto(projetoPesquisaDTO, projetoPesquisa);

        ProjetoPesquisa salvo = repository.save(projetoPesquisa);

        return mapper.toDTO(salvo);
    }
}
