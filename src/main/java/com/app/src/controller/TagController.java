package com.app.src.controller;

import com.app.src.model.Tag;
import com.app.src.dto.TagDTO;
import com.app.src.mapper.TagMapper;
import com.app.src.repository.TagRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todas as tags
    @GetMapping("/listarTags")
    public List<TagDTO> listarTodas() {
        return tagRepository.findAll().stream()
                .map(TagMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar tag por ID
    @GetMapping("/listarTag/{id}")
    public TagDTO buscarPorId(@PathVariable Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag não encontrada com id: " + id));
        return TagMapper.toDTO(tag);
    }

    // Criar nova tag
    public TagDTO criar(@RequestBody TagDTO tagDTO) {
        Tag tag = TagMapper.toEntity(tagDTO);
    
        if (tag.getPesquisador() == null || tag.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }
    
        if (!pesquisadorRepository.existsById(tag.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + tag.getPesquisador().getId());
        }
    
        Tag salvo = tagRepository.save(tag);
        return TagMapper.toDTO(salvo);
    }

    // Atualizar tag
    @PutMapping("/alterarTag/{id}")
    public TagDTO atualizar(@PathVariable Integer id, @RequestBody Tag tagAtualizada) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag não encontrada com id: " + id));

        tag.setListaTags(tagAtualizada.getListaTags());

        Tag salvo = tagRepository.save(tag);
        return TagMapper.toDTO(salvo);
    }

    // Deletar tag
    @DeleteMapping("/excluirTag/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!tagRepository.existsById(id)) {
            throw new NoSuchElementException("Tag não encontrada com id: " + id);
        }
        tagRepository.deleteById(id);
    }
}