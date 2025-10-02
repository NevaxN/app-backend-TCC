package com.app.src.controllers;

import com.app.src.dto.PremiacaoDTO;
import com.app.src.services.PremiacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/premiacoes")
public class PremiacaoController {
        
    @Autowired
    private PremiacaoService premiacaoService;

    

    @GetMapping("/listarPremiacoes")
    public ResponseEntity<List<PremiacaoDTO>> listarTodos() {
        return ResponseEntity.ok(premiacaoService.buscarTodos()); 
    }

    // Buscar endereço por ID
    @GetMapping("/listarPremiacao/{id}")
    public ResponseEntity<PremiacaoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(premiacaoService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarPremiacao")
    public ResponseEntity<PremiacaoDTO> criar(@RequestBody PremiacaoDTO premiacaoDTO) {
        return ResponseEntity.ok(premiacaoService.salvar(premiacaoDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarPremiacao/{id}")
    public ResponseEntity<PremiacaoDTO> atualizar(@PathVariable Integer id, @RequestBody PremiacaoDTO premiacaoAtualizada) {
        return ResponseEntity.ok(premiacaoService.atualizar(id, premiacaoAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirPremiacao/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(premiacaoService.excluir(id));
    }
}
