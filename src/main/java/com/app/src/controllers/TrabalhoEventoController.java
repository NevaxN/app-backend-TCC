package com.app.src.controllers;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.services.TrabalhoEventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabalhos-evento")
public class TrabalhoEventoController {

    @Autowired
    private TrabalhoEventoService trabalhoEventoService;

    // Listar todos os trabalhos em eventos
    @GetMapping("/listarTrabalhosEvento")
    public ResponseEntity<List<TrabalhoEventoDTO>> listarTodos() {
        return ResponseEntity.ok(trabalhoEventoService.buscarTodos());
    }

    // Buscar trabalho por ID
    @GetMapping("/listarTrabalhoEvento/{id}")
    public ResponseEntity<TrabalhoEventoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(trabalhoEventoService.buscarPorId(id));
    }

    // Criar novo trabalho de evento
    @PostMapping("/salvarTrabalhoEvento")
    public ResponseEntity<TrabalhoEventoDTO> criar(@RequestBody TrabalhoEventoDTO trabalhoEventoDTO) {
        return ResponseEntity.ok(trabalhoEventoService.salvar(trabalhoEventoDTO));
    }

    // Atualizar trabalho de evento
    @PutMapping("/alterarTrabalhoEvento/{id}")
    public ResponseEntity<TrabalhoEventoDTO> atualizar(@PathVariable Integer id, @RequestBody TrabalhoEventoDTO dadosAtualizados) {
        return ResponseEntity.ok(trabalhoEventoService.atualizar(id, dadosAtualizados));
    }

    // Deletar trabalho de evento
    @DeleteMapping("/excluirTrabalhoEvento/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(trabalhoEventoService.excluir(id));
    }
}
