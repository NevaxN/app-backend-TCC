package com.app.src.controllers;

import com.app.src.dto.EventoDTO;
import com.app.src.services.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    
    @Autowired
    private EventoService eventoService;

    

    @GetMapping("/listarEventos")
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        return ResponseEntity.ok(eventoService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEvento/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarEvento")
    public ResponseEntity<EventoDTO> salvar(@RequestBody EventoDTO eventoDTO) {
        return ResponseEntity.ok(eventoService.salvar(eventoDTO));
    }

    // Atualizar endereço
    @PutMapping("/alterarEvento/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Integer id, @RequestBody EventoDTO eventoAtualizada) {
        return ResponseEntity.ok(eventoService.atualizar(id, eventoAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirEvento/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        return ResponseEntity.ok(eventoService.excluir(id));
    }
}
