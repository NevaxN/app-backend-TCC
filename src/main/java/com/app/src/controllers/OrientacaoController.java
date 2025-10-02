package com.app.src.controllers;

import com.app.src.dto.OrientacaoDTO;
import com.app.src.services.OrientacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orientacoes")
public class OrientacaoController {
    @Autowired
    private OrientacaoService orientacaoService;

    // Listar todos os endereços
    @GetMapping("/listarOrientacoes")
    public ResponseEntity<List<OrientacaoDTO>> listarTodos() {
        return ResponseEntity.ok(orientacaoService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarOrientacao/{id}")
    public ResponseEntity<OrientacaoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(orientacaoService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarOrientacao")
    public ResponseEntity<OrientacaoDTO> criar(@RequestBody OrientacaoDTO orientacaoDTO) {
        return ResponseEntity.ok(orientacaoService.salvar(orientacaoDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarOrientacao/{id}")
    public ResponseEntity<OrientacaoDTO> atualizar(@PathVariable Integer id, @RequestBody OrientacaoDTO orientacaoAtualizado) {
        return ResponseEntity.ok(orientacaoService.atualizar(id, orientacaoAtualizado));
    }

    // Deletar endereço
    @DeleteMapping("/excluirOrientacao/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(orientacaoService.excluir(id));
    }
}
