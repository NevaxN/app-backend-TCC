package com.app.src.controller;

import com.app.src.model.Tag;
import com.app.src.repository.TagRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todas as tags
    @GetMapping
    public List<Tag> listarTodas() {
        return tagRepository.findAll();
    }

    // Buscar tag por ID
    @GetMapping("/{id}")
    public Tag buscarPorId(@PathVariable Integer id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag não encontrada com id: " + id));
    }

    // Criar nova tag
    @PostMapping
    public Tag criar(@RequestBody Tag tag) {
        if (!pesquisadorRepository.existsById(tag.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + tag.getPesquisador().getId());
        }
        return tagRepository.save(tag);
    }

    // Atualizar tag
    @PutMapping("/{id}")
    public Tag atualizar(@PathVariable Integer id, @RequestBody Tag tagAtualizada) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tag não encontrada com id: " + id));

        tag.setListaTags(tagAtualizada.getListaTags());

        return tagRepository.save(tag);
    }

    // Deletar tag
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!tagRepository.existsById(id)) {
            throw new NoSuchElementException("Tag não encontrada com id: " + id);
        }
        tagRepository.deleteById(id);
    }
}