package com.app.src.controllers;

import com.app.src.dto.ProducaoBibliograficaDTO;
import com.app.src.mappers.ProducaoBibliograficaMapper;
import com.app.src.models.ProducaoBibliografica;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.ProducaoBibliograficaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producoes")
public class ProducaoBibliograficaController {
    
    @Autowired
    private ProducaoBibliograficaRepository producaoBibliograficaRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping("/listarProducoes")
    public List<ProducaoBibliograficaDTO> listarTodos() {
        return producaoBibliograficaRepository.findAll().stream()
                .map(ProducaoBibliograficaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarProducao/{id}")
    public ProducaoBibliograficaDTO buscarPorId(@PathVariable Integer id) {
        ProducaoBibliografica producaoBibliografica = producaoBibliograficaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id));
        return ProducaoBibliograficaMapper.toDTO(producaoBibliografica);
    }

    // Criar novo endereço
    @PostMapping("/salvarProducao")
    public ProducaoBibliograficaDTO criar(@RequestBody ProducaoBibliograficaDTO producaoBibliograficaDTO) {
        ProducaoBibliografica producaoBibliografica = ProducaoBibliograficaMapper.toEntity(producaoBibliograficaDTO);
        
        if (producaoBibliografica.getPesquisador() == null || producaoBibliografica.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(producaoBibliografica.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + producaoBibliografica.getPesquisador().getId());
        }

        ProducaoBibliografica salvo = producaoBibliograficaRepository.save(producaoBibliografica);
        return ProducaoBibliograficaMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarProducao/{id}")
    public ProducaoBibliograficaDTO atualizar(@PathVariable Integer id, @RequestBody ProducaoBibliografica producaoBibliograficaAtualizado) {
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

        ProducaoBibliografica salvo = producaoBibliograficaRepository.save(producaoBibliografica);
        return ProducaoBibliograficaMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirProducao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!producaoBibliograficaRepository.existsById(id)) {
            throw new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id);
        }
        producaoBibliograficaRepository.deleteById(id);
    }
}
