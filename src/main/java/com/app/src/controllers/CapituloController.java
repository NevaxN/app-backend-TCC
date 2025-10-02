package com.app.src.controllers;

import com.app.src.dto.CapituloDTO;
import com.app.src.services.CapituloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capitulos")
public class CapituloController {

    @Autowired
    private CapituloService capituloService;

    // Listar todos os capítulos
    @GetMapping("/listarCapitulos")
    public ResponseEntity<List<CapituloDTO>> listarTodos() {
        return ResponseEntity.ok(capituloService.buscarTodos());
    }

    // Buscar capítulo por ID
    @GetMapping("/listarCapitulo/{id}")
    public ResponseEntity<CapituloDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(capituloService.buscarPorId(id));
    }

    // Criar novo capítulo
    @PostMapping("/salvarCapitulo")
    public ResponseEntity<CapituloDTO> salvar(@RequestBody CapituloDTO capituloDTO) {
        return ResponseEntity.ok(capituloService.salvar(capituloDTO));
    }

    // Atualizar capítulo
    @PutMapping("/alterarCapitulo/{id}")
    public ResponseEntity<CapituloDTO> atualizar(@PathVariable Integer id, @RequestBody CapituloDTO dadosAtualizados) {
        return ResponseEntity.ok(capituloService.atualizar(id, dadosAtualizados));
    }

    // Deletar capítulo
    @DeleteMapping("/excluirCapitulo/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(capituloService.excluir(id));
    }
}
