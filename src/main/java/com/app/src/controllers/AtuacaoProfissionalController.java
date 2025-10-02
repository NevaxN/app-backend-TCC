package com.app.src.controllers;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.services.AtuacaoProfissionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atuacoesProfissionais")
public class AtuacaoProfissionalController {

    @Autowired
    private AtuacaoProfissionalService atuacaoProfissionalService;

    @GetMapping("/listarAtuacaoesProfissionais")
    public ResponseEntity<List<AtuacaoProfissionalDTO>> listarTodos() {
        return ResponseEntity.ok(atuacaoProfissionalService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarAtuacaoProfissional/{id}")
    public ResponseEntity<AtuacaoProfissionalDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(atuacaoProfissionalService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarAtuacaoProfissional")
    public ResponseEntity<AtuacaoProfissionalDTO> criar(@RequestBody AtuacaoProfissionalDTO atuacaoProfissionalDTO) {
        return ResponseEntity.ok(atuacaoProfissionalService.salvar(atuacaoProfissionalDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarAtuacaoProfissional/{id}")
    public ResponseEntity<AtuacaoProfissionalDTO> atualizar(@PathVariable Integer id, @RequestBody AtuacaoProfissionalDTO atuacaoProfissionalAtualizada) {
        return ResponseEntity.ok(atuacaoProfissionalService.atualizar(id, atuacaoProfissionalAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirAtuacaoProfissional/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(atuacaoProfissionalService.excluir(id));
    }
}
