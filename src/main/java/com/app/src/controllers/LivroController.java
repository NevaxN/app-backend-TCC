package com.app.src.controllers;

import com.app.src.dto.LivroDTO;
import com.app.src.services.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Listar todos os livros
    @GetMapping("/listarLivros")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.buscarTodos());
    }

    // Buscar livro por ID
    @GetMapping("/listarLivro/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    // Criar novo livro
    @PostMapping("/salvarLivro")
    public ResponseEntity<LivroDTO> criar(@RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(livroService.salvar(livroDTO));
    }

    // Atualizar livro
    @PutMapping("/alterarLivro/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Integer id, @RequestBody LivroDTO dadosAtualizados) {
        return ResponseEntity.ok(livroService.atualizar(id, dadosAtualizados));
    }

    // Deletar livro
    @DeleteMapping("/excluirLivro/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(livroService.excluir(id));
    }
}
