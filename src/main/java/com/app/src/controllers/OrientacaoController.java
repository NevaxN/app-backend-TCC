package com.app.src.controllers;

import com.app.src.dto.OrientacaoDTO;
import com.app.src.mappers.OrientacaoMapper;
import com.app.src.models.Orientacao;
import com.app.src.repositories.OrientacaoRepository;
import com.app.src.repositories.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orientacoes")
public class OrientacaoController {
    @Autowired
    private OrientacaoRepository orientacaoRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping("/listarOrientacoes")
    public List<OrientacaoDTO> listarTodos() {
        return orientacaoRepository.findAll().stream()
                .map(OrientacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarOrientacao/{id}")
    public OrientacaoDTO buscarPorId(@PathVariable Integer id) {
        Orientacao orientacao = orientacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orientação não encontrado com id: " + id));

        return OrientacaoMapper.toDTO(orientacao);
    }

    // Criar novo endereço
    @PostMapping("/salvarOrientacao")
    public OrientacaoDTO criar(@RequestBody OrientacaoDTO orientacaoDTO) {
        Orientacao orientacao = OrientacaoMapper.toEntity(orientacaoDTO);

        if (orientacao.getPesquisador() == null || orientacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(orientacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + orientacao.getPesquisador().getId());
        }

        Orientacao salvo = orientacaoRepository.save(orientacao);

        return OrientacaoMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarOrientacao/{id}")
    public OrientacaoDTO atualizar(@PathVariable Integer id, @RequestBody Orientacao orientacaoAtualizado) {
        Orientacao orientacao = orientacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orientação não encontrada com id: " + id));

        orientacao.setTipo(orientacaoAtualizado.getTipo());
        orientacao.setNomeOrientado(orientacaoAtualizado.getNomeOrientado());
        orientacao.setTituloTrabalho(orientacaoAtualizado.getTituloTrabalho());
        orientacao.setInstituicao(orientacaoAtualizado.getInstituicao());
        orientacao.setAnoInicio(orientacaoAtualizado.getAnoInicio());
        orientacao.setAnoFim(orientacaoAtualizado.getAnoFim());
        orientacao.setDestaque(orientacaoAtualizado.getDestaque());

        Orientacao salvo = orientacaoRepository.save(orientacao);

        return OrientacaoMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirOrientacao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!orientacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Orientação não encontrado com id: " + id);
        }
        orientacaoRepository.deleteById(id);
    }
}
