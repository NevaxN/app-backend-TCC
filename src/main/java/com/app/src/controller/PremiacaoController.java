package com.app.src.controller;

import com.app.src.model.Premiacao;
import com.app.src.dto.PremiacaoDTO;
import com.app.src.mapper.PremiacaoMapper;
import com.app.src.repository.PremiacaoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/premiacoes")
public class PremiacaoController {
        
    @Autowired
    private PremiacaoRepository premiacaoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarPremiacoes")
    public List<PremiacaoDTO> listarTodos() {
        return premiacaoRepository.findAll().stream()
                .map(PremiacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarPremiacao/{id}")
    public PremiacaoDTO buscarPorId(@PathVariable Integer id) {
        Premiacao premiacao = premiacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premiacao não encontrado com id: " + id));

        return PremiacaoMapper.toDTO(premiacao);
    }

    // Criar novo endereço
    @PostMapping("/salvarPremiacao")
    public PremiacaoDTO criar(@RequestBody PremiacaoDTO premiacaoDTO) {
        Premiacao premiacao = PremiacaoMapper.toEntity(premiacaoDTO);

        if (premiacao.getPesquisador() == null || premiacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(premiacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + premiacao.getPesquisador().getId());
        }

        Premiacao salvo = premiacaoRepository.save(premiacao);
        return PremiacaoMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarPremiacao/{id}")
    public PremiacaoDTO atualizar(@PathVariable Integer id, @RequestBody Premiacao premiacaoAtualizada) {
        Premiacao premiacao = premiacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Premiacao não encontrado com id: " + id));

        premiacao.setTitulo(premiacaoAtualizada.getTitulo());
        premiacao.setInstituicao(premiacaoAtualizada.getInstituicao());
        premiacao.setAno(premiacaoAtualizada.getAno());

        Premiacao salvo = premiacaoRepository.save(premiacao);
        return PremiacaoMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirPremiacao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!premiacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Premiacao não encontrado com id: " + id);
        }
        premiacaoRepository.deleteById(id);
    }
}
