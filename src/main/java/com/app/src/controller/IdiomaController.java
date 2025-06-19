package com.app.src.controller;

import com.app.src.model.Idioma;
import com.app.src.repository.IdiomaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/idiomas")
public class IdiomaController {
    
    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Idioma> listarTodos() {
        return idiomaRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Idioma buscarPorId(@PathVariable Integer id) {
        return idiomaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Idioma não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Idioma criar(@RequestBody Idioma idioma) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(idioma.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + idioma.getPesquisador().getId());
        }
        return idiomaRepository.save(idioma);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Idioma atualizar(@PathVariable Integer id, @RequestBody Idioma idiomaAtualizada) {
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Idioma não encontrado com id: " + id));

        idioma.setIdioma(idiomaAtualizada.getIdioma());
        idioma.setLeitura(idiomaAtualizada.getLeitura());
        idioma.setEscrita(idiomaAtualizada.getEscrita());
        idioma.setFala(idiomaAtualizada.getFala());

        return idiomaRepository.save(idioma);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!idiomaRepository.existsById(id)) {
            throw new NoSuchElementException("Idioma não encontrado com id: " + id);
        }
        idiomaRepository.deleteById(id);
    }
}
