package com.app.src.controller;

import com.app.src.model.Lista;
import com.app.src.repository.ListaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Lista> listarTodos() {
        return listaRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Lista buscarPorId(@PathVariable Integer id) {
        return listaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Lista criar(@RequestBody Lista lista) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(lista.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + lista.getPesquisador().getId());
        }
        return listaRepository.save(lista);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Lista atualizar(@PathVariable Integer id, @RequestBody Lista listaAtualizada) {
        Lista lista = listaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lista não encontrado com id: " + id));

        lista.setNomeLista(listaAtualizada.getNomeLista());

        return listaRepository.save(lista);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!listaRepository.existsById(id)) {
            throw new NoSuchElementException("Lista não encontrado com id: " + id);
        }
        listaRepository.deleteById(id);
    }
}
