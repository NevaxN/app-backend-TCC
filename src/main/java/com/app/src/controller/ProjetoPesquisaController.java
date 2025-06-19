package com.app.src.controller;

import com.app.src.model.ProjetoPesquisa;
import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.mapper.ProjetoPesquisaMapper;
import com.app.src.repository.ProjetoPesquisaRepository;
import com.app.src.repository.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projetos_pesquisa")
public class ProjetoPesquisaController {
    
    @Autowired
    private ProjetoPesquisaRepository projetoPesquisaRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarProjetos")
    public List<ProjetoPesquisaDTO> listarTodos() {
        return projetoPesquisaRepository.findAll().stream()
                .map(ProjetoPesquisaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/listarProjeto/{id}")
    public ProjetoPesquisaDTO buscarPorId(@PathVariable Integer id) {
        ProjetoPesquisa projetoPesquisa = projetoPesquisaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id));
        return ProjetoPesquisaMapper.toDTO(projetoPesquisa);
    }

    @PostMapping("/salvarProjeto")
    public ProjetoPesquisaDTO criar(@RequestBody ProjetoPesquisaDTO projetoPesquisaDTO) {
        ProjetoPesquisa projetoPesquisa = ProjetoPesquisaMapper.toEntity(projetoPesquisaDTO);
        
        if (projetoPesquisa.getPesquisador() == null || projetoPesquisa.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(projetoPesquisa.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + projetoPesquisa.getPesquisador().getId());
        }

        ProjetoPesquisa salvo = projetoPesquisaRepository.save(projetoPesquisa);

        return ProjetoPesquisaMapper.toDTO(salvo);
    }

    @PutMapping("/alterarProjeto/{id}")
    public ProjetoPesquisaDTO atualizar(@PathVariable Integer id, @RequestBody ProjetoPesquisa projetoPesquisaAtualizado) {
        ProjetoPesquisa projetoPesquisa = projetoPesquisaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id));

        projetoPesquisa.setTitulo(projetoPesquisaAtualizado.getTitulo());
        projetoPesquisa.setDescricao(projetoPesquisaAtualizado.getDescricao());
        projetoPesquisa.setInstituicao(projetoPesquisaAtualizado.getInstituicao());
        projetoPesquisa.setAnoInicio(projetoPesquisaAtualizado.getAnoInicio());
        projetoPesquisa.setAnoFim(projetoPesquisaAtualizado.getAnoFim());
        projetoPesquisa.setFinanciador(projetoPesquisaAtualizado.getFinanciador());
        projetoPesquisa.setDestaque(projetoPesquisaAtualizado.getDestaque());

        ProjetoPesquisa salvo = projetoPesquisaRepository.save(projetoPesquisa);

        return ProjetoPesquisaMapper.toDTO(salvo); 
    }

    @DeleteMapping("/excluirProjeto/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!projetoPesquisaRepository.existsById(id)) {
            throw new NoSuchElementException("Projeto Pesquisa não encontrado com id: " + id);
        }
        projetoPesquisaRepository.deleteById(id);
    }
}
