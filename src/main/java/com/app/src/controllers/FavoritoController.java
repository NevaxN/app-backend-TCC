package com.app.src.controllers;

import com.app.src.dto.FavoritoDTO;
import com.app.src.services.FavoritoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
        
    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/listarFavoritos")
    public ResponseEntity<List<FavoritoDTO>> listarTodos() {
        return ResponseEntity.ok(favoritoService.buscarTodos());
    }

    @GetMapping("/listarFavorito/{id}")
    public ResponseEntity<FavoritoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(favoritoService.buscarPorId(id));
    }

    @PostMapping("/salvarFavorito")
    public ResponseEntity<FavoritoDTO> criar(@RequestBody FavoritoDTO favoritoDTO) {
        return ResponseEntity.ok(favoritoService.salvar(favoritoDTO));
    }

    @DeleteMapping("/excluirFavorito/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        return ResponseEntity.ok(favoritoService.excluir(id));
    }
}
