package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.TagDTO;
import com.app.src.mappers.TagMapper;
import com.app.src.models.Tag;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.TagRepository;

@Service
public class TagService extends GenericCrudService<Tag, TagDTO, Integer, TagRepository>{

    @Autowired
    private PesquisadorRepository pesquisadorRepository;
    
    public TagService(TagRepository repository, TagMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "tags", key = "#id")
    public TagDTO buscarPorId(Integer id){
        return super.buscarPorId(id);        
    }

    public TagDTO buscarPorIdPesquisador (Integer idPesquisador) {
        Tag tag = repository.findByPesquisadorId(idPesquisador);
        return mapper.toDTO(tag);
    }

    @Override
    public TagDTO salvar(TagDTO tagDTO){

        Tag tag = mapper.toEntity(tagDTO);
    
        if (tag.getPesquisador() == null || tag.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }
    
        if (!pesquisadorRepository.existsById(tag.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + tag.getPesquisador().getId());
        }

        return super.salvar(tagDTO);
    }

    @CachePut(value = "tags", key = "#id")
    public TagDTO atualizar(Integer id, TagDTO tagDTO){
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag não encontrada com id: " + id));

        ((TagMapper) mapper).updateEntityFromDto(tagDTO, tag);

        Tag salvo = repository.save(tag);

        return mapper.toDTO(salvo);
    }
}
