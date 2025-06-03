package com.app.src.controller;

import com.app.src.model.Seguidor;
import com.app.src.repository.SeguidorRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/seguidores")
public class SeguidorController {
        
    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Seguidor> listarTodos() {
        return seguidorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Seguidor buscarPorId(@PathVariable Integer id) {
        return seguidorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seguidor não encontrado com id: " + id));
    }

    @PostMapping
    public Seguidor criar(@RequestBody Seguidor seguidor) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(seguidor.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + seguidor.getPesquisador().getId());
        }
        return seguidorRepository.save(seguidor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!seguidorRepository.existsById(id)) {
            throw new NoSuchElementException("Seguidor não encontrado com id: " + id);
        }
        seguidorRepository.deleteById(id);
    }
}
