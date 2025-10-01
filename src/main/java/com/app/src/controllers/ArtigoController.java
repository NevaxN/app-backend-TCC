package com.app.src.controllers;

import com.app.src.dto.ArtigoDTO;
import com.app.src.models.Artigo;
import com.app.src.services.ArtigoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artigos")
public class ArtigoController {

    @Autowired
    private ArtigoService artigoService;

    // Listar todos os artigos
    @GetMapping("/listarArtigos")
    public ResponseEntity<List<ArtigoDTO>> listarTodos() {
        return ResponseEntity.ok(artigoService.buscarTodos()); 
    }

    // Buscar artigo por ID
    @GetMapping("/listarArtigo/{id}")
    public ResponseEntity<ArtigoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(artigoService.buscarPorId(id));
    }

    // Criar novo artigo
    @PostMapping("/salvarArtigo")
    public ResponseEntity<ArtigoDTO> criar(@RequestBody ArtigoDTO artigoDTO) {
        return ResponseEntity.ok(artigoService.salvar(artigoDTO));
    }

    // Atualizar artigo
    @PutMapping("/alterarArtigo/{id}")
    public ResponseEntity<ArtigoDTO> atualizar(@PathVariable Integer id, @RequestBody Artigo dadosAtualizados) {
        return ResponseEntity.ok(artigoService.atualizar(id, dadosAtualizados));
    }

    // Deletar artigo
    @DeleteMapping("/excluirArtigo/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(artigoService.excluir(id));
    }
}
