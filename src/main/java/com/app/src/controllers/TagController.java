package com.app.src.controllers;

import com.app.src.dto.TagDTO;
import com.app.src.services.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // Listar todas as tags
    @GetMapping("/listarTags")
    public ResponseEntity<List<TagDTO>> listarTodas() {
        return ResponseEntity.ok(tagService.buscarTodos());
    }

    // Buscar tag por ID
    @GetMapping("/listarTag/{id}")
    public ResponseEntity<TagDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.buscarPorId(id));
    }

    // Criar nova tag
    @PostMapping("/salvarTag")
    public ResponseEntity<TagDTO> criar(@RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.salvar(tagDTO));
    }

    // Atualizar tag
    @PutMapping("/alterarTag/{id}")
    public ResponseEntity<TagDTO> atualizar(@PathVariable Integer id, @RequestBody TagDTO tagAtualizada) {
        return ResponseEntity.ok(tagService.atualizar(id, tagAtualizada));
    }

    // Deletar tag
    @DeleteMapping("/excluirTag/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(tagService.excluir(id));
    }
}