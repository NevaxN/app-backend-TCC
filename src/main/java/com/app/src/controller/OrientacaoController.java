package com.app.src.controller;

import com.app.src.model.Orientacao;
import com.app.src.repository.OrientacaoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orientacoes")
public class OrientacaoController {
    @Autowired
    private OrientacaoRepository orientacaoRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping
    public List<Orientacao> listarTodos() {
        return orientacaoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Orientacao buscarPorId(@PathVariable Integer id) {
        return orientacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orientação não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Orientacao criar(@RequestBody Orientacao orientacao) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(orientacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + orientacao.getPesquisador().getId());
        }
        return orientacaoRepository.save(orientacao);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Orientacao atualizar(@PathVariable Integer id, @RequestBody Orientacao orientacaoAtualizado) {
        Orientacao orientacao = orientacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orientação não encontrada com id: " + id));

        orientacao.setTipo(orientacaoAtualizado.getTipo());
        orientacao.setNomeOrientado(orientacaoAtualizado.getNomeOrientado());
        orientacao.setTituloTrabalho(orientacaoAtualizado.getTituloTrabalho());
        orientacao.setInstituicao(orientacaoAtualizado.getInstituicao());
        orientacao.setAnoInicio(orientacaoAtualizado.getAnoInicio());
        orientacao.setAnoFim(orientacaoAtualizado.getAnoFim());
        orientacao.setDestaque(orientacaoAtualizado.getDestaque());

        return orientacaoRepository.save(orientacao);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!orientacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Orientação não encontrado com id: " + id);
        }
        orientacaoRepository.deleteById(id);
    }
}
