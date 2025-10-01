package com.app.src.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.ArtigoDTO;
import com.app.src.mappers.ArtigoMapper;
import com.app.src.models.Artigo;
import com.app.src.repositories.ArtigoRepository;

@Service
public class ArtigoService {
    
    @Autowired
    private ArtigoRepository artigoRepository;

    public List<ArtigoDTO> buscarTodos(){
        return artigoRepository.findAll().stream()
                .map(ArtigoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "artigos", key = "#id")
    public ArtigoDTO buscarPorId(Integer id){
        Artigo artigo = artigoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Artigo não encontrado com id: " + id));

        return ArtigoMapper.toDTO(artigo);
    }

    public ArtigoDTO salvar(ArtigoDTO artigoDTO){
        Artigo artigo = ArtigoMapper.toEntity(artigoDTO);

        Artigo salvo = artigoRepository.save(artigo);

        return ArtigoMapper.toDTO(salvo);
    }

    public ArtigoDTO atualizar(Integer id, Artigo dadosAtualizados){
        ArtigoDTO existenteDTO = buscarPorId(id);
        Artigo existente = ArtigoMapper.toEntity(existenteDTO);

        existente.setSequenciaProducao(dadosAtualizados.getSequenciaProducao());
        existente.setPesquisador(dadosAtualizados.getPesquisador());
        existente.setAutores(dadosAtualizados.getAutores());
        existente.setAno(dadosAtualizados.getAno());
        existente.setDestaque(dadosAtualizados.getDestaque());
        existente.setTitulo(dadosAtualizados.getTitulo());
        existente.setPeriodico(dadosAtualizados.getPeriodico());
        existente.setDoi(dadosAtualizados.getDoi());
        existente.setIdioma(dadosAtualizados.getIdioma());

        Artigo salvo = artigoRepository.save(existente);

        return ArtigoMapper.toDTO(salvo);
    }

    public String excluir(Integer id){
        if (!artigoRepository.existsById(id)) {
            throw new NoSuchElementException("Artigo não encontrado com id: " + id);
        }
        artigoRepository.deleteById(id);

        return "Artigo com id: " + id + " excluido com sucesso";
    }
}
