package com.app.src.controller;

import com.app.src.model.Premiacao;
import com.app.src.repository.PremiacaoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/premiacoes")
public class PremiacaoController {
        
    @Autowired
    private PremiacaoRepository premiacaoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Premiacao> listarTodos() {
        return premiacaoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Premiacao buscarPorId(@PathVariable Integer id) {
        return premiacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premiacao não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Premiacao criar(@RequestBody Premiacao premiacao) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(premiacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + premiacao.getPesquisador().getId());
        }
        return premiacaoRepository.save(premiacao);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Premiacao atualizar(@PathVariable Integer id, @RequestBody Premiacao premiacaoAtualizada) {
        Premiacao premiacao = premiacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premiacao não encontrado com id: " + id));

        premiacao.setTitulo(premiacaoAtualizada.getTitulo());
        premiacao.setInstituicao(premiacaoAtualizada.getInstituicao());
        premiacao.setAno(premiacaoAtualizada.getAno());

        return premiacaoRepository.save(premiacao);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!premiacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Premiacao não encontrado com id: " + id);
        }
        premiacaoRepository.deleteById(id);
    }
}
