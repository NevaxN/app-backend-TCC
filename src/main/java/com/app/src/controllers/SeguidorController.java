package com.app.src.controllers;

import com.app.src.dto.SeguidorDTO;
import com.app.src.models.Seguidor;
import com.app.src.services.SeguidorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SeguidorDTO> seguir(@RequestBody SeguidorDTO seguidorDTO) {
        SeguidorDTO novoSeguidor = seguidorService.salvar(seguidorDTO);
        return new ResponseEntity<>(novoSeguidor, HttpStatus.CREATED);
    }

    @DeleteMapping("/excluirSeguidor")
    public ResponseEntity<Void> deixarDeSeguir(
            @RequestParam Integer usuarioId, 
            @RequestParam Integer pesquisadorId) {
        
        seguidorService.deixarDeSeguir(usuarioId, pesquisadorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/seguindo")
    public ResponseEntity<List<Seguidor>> listarQuemUsuarioSegue(@PathVariable Integer usuarioId) {
        List<Seguidor> listaDeSeguindo = seguidorService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(listaDeSeguindo);
    }
}
