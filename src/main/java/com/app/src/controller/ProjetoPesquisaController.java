package com.app.src.controller;

import com.app.src.model.ProjetoPesquisa;
import com.app.src.repository.ProjetoPesquisaRepository;
import com.app.src.repository.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/projetos_pesquisa")
public class ProjetoPesquisaController {
    
    @Autowired
    private ProjetoPesquisaRepository projetoPesquisaRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<ProjetoPesquisa> listarTodos() {
        return projetoPesquisaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProjetoPesquisa buscarPorId(@PathVariable Integer id) {
        return projetoPesquisaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id));
    }

    @PostMapping
    public ProjetoPesquisa criar(@RequestBody ProjetoPesquisa projetoPesquisa) {
        if (!pesquisadorRepository.existsById(projetoPesquisa.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + projetoPesquisa.getPesquisador().getId());
        }
        return projetoPesquisaRepository.save(projetoPesquisa);
    }

    @PutMapping("/{id}")
    public ProjetoPesquisa atualizar(@PathVariable Integer id, @RequestBody ProjetoPesquisa projetoPesquisaAtualizado) {
        ProjetoPesquisa projetoPesquisa = projetoPesquisaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id));

        projetoPesquisa.setTitulo(projetoPesquisaAtualizado.getTitulo());
        projetoPesquisa.setDescricao(projetoPesquisaAtualizado.getDescricao());
        projetoPesquisa.setInstituicao(projetoPesquisaAtualizado.getInstituicao());
        projetoPesquisa.setAnoInicio(projetoPesquisaAtualizado.getAnoInicio());
        projetoPesquisa.setAnoFim(projetoPesquisaAtualizado.getAnoFim());
        projetoPesquisa.setFinanciador(projetoPesquisaAtualizado.getFinanciador());
        projetoPesquisa.setDestaque(projetoPesquisaAtualizado.getDestaque());

        return projetoPesquisaRepository.save(projetoPesquisa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!projetoPesquisaRepository.existsById(id)) {
            throw new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id);
        }
        projetoPesquisaRepository.deleteById(id);
    }
}
