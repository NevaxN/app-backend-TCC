package com.app.src.controller;

import com.app.src.model.Graduacao;
import com.app.src.repository.GraduacaoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/graduacoes")
public class GraduacaoController {

    @Autowired
    private GraduacaoRepository graduacaoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Graduacao> listarTodos() {
        return graduacaoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Graduacao buscarPorId(@PathVariable Integer id) {
        return graduacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Graduacao criar(@RequestBody Graduacao graduacao) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(graduacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + graduacao.getPesquisador().getId());
        }
        return graduacaoRepository.save(graduacao);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Graduacao atualizar(@PathVariable Integer id, @RequestBody Graduacao graduacaoAtualizada) {
        Graduacao graduacao = graduacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com id: " + id));

        graduacao.setNivel(graduacaoAtualizada.getNivel());
        graduacao.setInstituicao(graduacaoAtualizada.getInstituicao());
        graduacao.setCurso(graduacaoAtualizada.getCurso());
        graduacao.setStatus(graduacaoAtualizada.getStatus());
        graduacao.setAnoInicio(graduacaoAtualizada.getAnoInicio());
        graduacao.setAnoConclusao(graduacaoAtualizada.getAnoConclusao());
        graduacao.setTituloTrabalho(graduacaoAtualizada.getTituloTrabalho());
        graduacao.setOrientador(graduacaoAtualizada.getOrientador());
        graduacao.setDestaque(graduacaoAtualizada.getDestaque());

        return graduacaoRepository.save(graduacao);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!graduacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Graduacao não encontrado com id: " + id);
        }
        graduacaoRepository.deleteById(id);
    }
    
}
