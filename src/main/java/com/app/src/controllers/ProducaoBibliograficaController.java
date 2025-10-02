package com.app.src.controllers;

import com.app.src.dto.ProducaoBibliograficaDTO;
import com.app.src.services.ProducaoBibliograficaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producoes")
public class ProducaoBibliograficaController {
    
    @Autowired
    private ProducaoBibliograficaService producaoBibliograficaService;
    
    

    // Listar todos os endereços
    @GetMapping("/listarProducoes")
    public ResponseEntity<List<ProducaoBibliograficaDTO>> listarTodos() {
        return ResponseEntity.ok(producaoBibliograficaService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarProducao/{id}")
    public ResponseEntity<ProducaoBibliograficaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(producaoBibliograficaService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarProducao")
    public ResponseEntity<ProducaoBibliograficaDTO> criar(@RequestBody ProducaoBibliograficaDTO producaoBibliograficaDTO) {
        return ResponseEntity.ok(producaoBibliograficaService.salvar(producaoBibliograficaDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarProducao/{id}")
    public ResponseEntity<ProducaoBibliograficaDTO> atualizar(@PathVariable Integer id, @RequestBody ProducaoBibliograficaDTO producaoBibliograficaAtualizado) {
        return ResponseEntity.ok(producaoBibliograficaService.atualizar(id, producaoBibliograficaAtualizado));
    }

    // Deletar endereço
    @DeleteMapping("/excluirProducao/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(producaoBibliograficaService.excluir(id));
    }
}
