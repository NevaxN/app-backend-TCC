package com.app.src.controllers;

import com.app.src.dto.EnderecoDTO;
import com.app.src.dto.EnderecoSemPesquisadorDTO;
import com.app.src.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Listar todos os endereços
    @GetMapping("/listarEnderecos")
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        return ResponseEntity.ok(enderecoService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEndereco/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarEndereco")
    public ResponseEntity<EnderecoDTO> salvar(@RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(enderecoService.salvar(enderecoDTO));  
    }

    // Atualizar endereço
    @PutMapping("/alterarEndereco/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(@PathVariable Integer id, @RequestBody EnderecoSemPesquisadorDTO enderecoAtualizado) {
        return ResponseEntity.ok(enderecoService.atualizarSemPesquisador(id, enderecoAtualizado));
    }

    // Deletar endereço
    @DeleteMapping("/excluirEndereco/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.excluir(id));
    }
}
