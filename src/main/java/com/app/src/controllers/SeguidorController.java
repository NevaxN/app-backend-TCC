package com.app.src.controllers;

import com.app.src.dto.SeguidorDTO;
import com.app.src.services.SeguidorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguidores")
public class SeguidorController {
        
    @Autowired
    private SeguidorService seguidorService;

    @GetMapping("/listarSeguidores")
    public ResponseEntity<List<SeguidorDTO>> listarTodos() {
        return ResponseEntity.ok(seguidorService.buscarTodos());
    }

    @GetMapping("/listarSeguidor/{id}")
    public ResponseEntity<SeguidorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(seguidorService.buscarPorId(id));
    }

    @PostMapping("/salvarSeguidor")
    public ResponseEntity<SeguidorDTO> criar(@RequestBody SeguidorDTO seguidorDTO) {
        return ResponseEntity.ok(seguidorService.salvar(seguidorDTO));
    }

    @DeleteMapping("/excluirSeguidor/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(seguidorService.excluir(id));
    }
}
