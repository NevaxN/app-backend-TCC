package com.app.src.controllers;

import com.app.src.dto.IdiomaDTO;
import com.app.src.services.IdiomaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idiomas")
public class IdiomaController {
    
    @Autowired
    private IdiomaService idiomaService;

    @GetMapping("/listarIdiomas")
    public ResponseEntity<List<IdiomaDTO>> listarTodos() {
        return ResponseEntity.ok(idiomaService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarIdioma/{id}")
    public ResponseEntity<IdiomaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(idiomaService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarIdioma")
    public ResponseEntity<IdiomaDTO> criar(@RequestBody IdiomaDTO idiomaDTO) {
        return ResponseEntity.ok(idiomaService.salvar(idiomaDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarIdioma/{id}")
    public ResponseEntity<IdiomaDTO> atualizar(@PathVariable Integer id, @RequestBody IdiomaDTO idiomaAtualizada) {
        return ResponseEntity.ok(idiomaService.atualizar(id, idiomaAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirIdioma/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(idiomaService.excluir(id));
    }
}
