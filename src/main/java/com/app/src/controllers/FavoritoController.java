package com.app.src.controllers;

import com.app.src.dto.FavoritoDTO;
import com.app.src.models.Favorito;
import com.app.src.models.Usuario;
import com.app.src.services.FavoritoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<FavoritoDTO> criar(
        @RequestBody FavoritoDTO favoritoDTO,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).build(); 
        }

        return ResponseEntity.ok(favoritoService.salvar(favoritoDTO, usuarioLogado));
    }

    @DeleteMapping("/excluirFavorito")
    public ResponseEntity<String> excluir(
        @RequestParam Integer usuarioId, 
        @RequestParam Integer pesquisadorId) {

        favoritoService.deixarDeSeguir(usuarioId, pesquisadorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/favorito")
    public ResponseEntity<List<Favorito>> listarQuemUsuarioSegue(@PathVariable Integer usuarioId) {
        List<Favorito> listaDeFavorito = favoritoService.buscarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(listaDeFavorito);
    }
}
