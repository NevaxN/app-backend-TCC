package com.app.src.controller;

import com.app.src.model.Graduacao;
import com.app.src.dto.GraduacaoDTO;
import com.app.src.mapper.GraduacaoMapper;
import com.app.src.repository.GraduacaoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/graduacoes")
public class GraduacaoController {

    @Autowired
    private GraduacaoRepository graduacaoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarGraduacoes")
    public List<GraduacaoDTO> listarTodos() {
        return graduacaoRepository.findAll().stream()
                .map(GraduacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarGraduacao/{id}")
    public GraduacaoDTO buscarPorId(@PathVariable Integer id) {
        Graduacao graduacao = graduacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com id: " + id));
            
        return GraduacaoMapper.toDTO(graduacao);
    }

    // Criar novo endereço
    @PostMapping("/salvarGraduacao")
    public GraduacaoDTO criar(@RequestBody GraduacaoDTO graduacaoDTO) {
        Graduacao graduacao = GraduacaoMapper.toEntity(graduacaoDTO);

        if (graduacao.getPesquisador() == null || graduacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(graduacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + graduacao.getPesquisador().getId());
        }

        Graduacao salvo = graduacaoRepository.save(graduacao);

        return GraduacaoMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarGraduacao/{id}")
    public GraduacaoDTO atualizar(@PathVariable Integer id, @RequestBody Graduacao graduacaoAtualizada) {
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

        Graduacao salvo = graduacaoRepository.save(graduacao);
        
        return GraduacaoMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirGraduacao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!graduacaoRepository.existsById(id)) {
            throw new NoSuchElementException("Graduacao não encontrado com id: " + id);
        }
        graduacaoRepository.deleteById(id);
    }
    
}
