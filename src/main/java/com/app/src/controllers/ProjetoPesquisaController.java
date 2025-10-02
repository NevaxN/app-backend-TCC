package com.app.src.controllers;

import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.services.ProjetoPesquisaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projetos_pesquisa")
public class ProjetoPesquisaController {
    
    @Autowired
    private ProjetoPesquisaService projetoPesquisaService;

    @GetMapping("/listarProjetos")
    public ResponseEntity<List<ProjetoPesquisaDTO>> listarTodos() {
        return ResponseEntity.ok(projetoPesquisaService.buscarTodos());
    }

    @GetMapping("/listarProjeto/{id}")
    public ResponseEntity<ProjetoPesquisaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(projetoPesquisaService.buscarPorId(id));
    }

    @PostMapping("/salvarProjeto")
    public ResponseEntity<ProjetoPesquisaDTO> criar(@RequestBody ProjetoPesquisaDTO projetoPesquisaDTO) {
        return ResponseEntity.ok(projetoPesquisaService.salvar(projetoPesquisaDTO));
    }

    @PutMapping("/alterarProjeto/{id}")
    public ResponseEntity<ProjetoPesquisaDTO> atualizar(@PathVariable Integer id, @RequestBody ProjetoPesquisaDTO projetoPesquisaAtualizado) {
        return ResponseEntity.ok(projetoPesquisaService.atualizar(id, projetoPesquisaAtualizado));
    }

    @DeleteMapping("/excluirProjeto/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(projetoPesquisaService.excluir(id));
    }
}
