package com.app.src.controller;

import com.app.src.model.ProducaoBibliografica;
import com.app.src.repository.ProducaoBibliograficaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/producoes_bibliograficas")
public class ProducaoBibliograficaController {
    
    @Autowired
    private ProducaoBibliograficaRepository producaoBibliograficaRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping
    public List<ProducaoBibliografica> listarTodos() {
        return producaoBibliograficaRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public ProducaoBibliografica buscarPorId(@PathVariable Integer id) {
        return producaoBibliograficaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public ProducaoBibliografica criar(@RequestBody ProducaoBibliografica producaoBibliografica) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(producaoBibliografica.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + producaoBibliografica.getPesquisador().getId());
        }
        return producaoBibliograficaRepository.save(producaoBibliografica);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public ProducaoBibliografica atualizar(@PathVariable Integer id, @RequestBody ProducaoBibliografica producaoBibliograficaAtualizado) {
        ProducaoBibliografica producaoBibliografica = producaoBibliograficaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id));

        producaoBibliografica.setTipo(producaoBibliograficaAtualizado.getTipo());
        producaoBibliografica.setTitulo(producaoBibliograficaAtualizado.getTitulo());
        producaoBibliografica.setAno(producaoBibliograficaAtualizado.getAno());
        producaoBibliografica.setVeiculoPublicacao(producaoBibliograficaAtualizado.getVeiculoPublicacao());
        producaoBibliografica.setIssn(producaoBibliograficaAtualizado.getIssn());
        producaoBibliografica.setDoi(producaoBibliograficaAtualizado.getDoi());
        producaoBibliografica.setAutores(producaoBibliograficaAtualizado.getAutores());
        producaoBibliografica.setDestaque(producaoBibliograficaAtualizado.getDestaque());

        return producaoBibliograficaRepository.save(producaoBibliografica);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!producaoBibliograficaRepository.existsById(id)) {
            throw new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id);
        }
        producaoBibliograficaRepository.deleteById(id);
    }
}
