package com.app.src.controller;

import com.app.src.model.Favorito;
import com.app.src.repository.FavoritoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
        
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Favorito> listarTodos() {
        return favoritoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Favorito buscarPorId(@PathVariable Integer id) {
        return favoritoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Favorito não encontrado com id: " + id));
    }

    @PostMapping
    public Favorito criar(@RequestBody Favorito favorito) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(favorito.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + favorito.getPesquisador().getId());
        }
        return favoritoRepository.save(favorito);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!favoritoRepository.existsById(id)) {
            throw new NoSuchElementException("Favorito não encontrado com id: " + id);
        }
        favoritoRepository.deleteById(id);
    }
}
