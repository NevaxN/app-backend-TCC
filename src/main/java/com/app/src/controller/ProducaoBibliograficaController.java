package com.app.src.controller;

import com.app.src.model.ProducaoBibliografica;
import com.app.src.dto.ProducaoBibliograficaDTO;
import com.app.src.mapper.ProducaoBibliograficaMapper;
import com.app.src.repository.ProducaoBibliograficaRepository;
import com.app.src.repository.PesquisadorRepository;
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
    public ProducaoBibliografica criar(@RequestBody ProducaoBibliografica producaoBibliografica) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(producaoBibliografica.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + producaoBibliografica.getPesquisador().getId());
        }
        return producaoBibliograficaRepository.save(producaoBibliografica);
    }

    // Atualizar endereço
    @PutMapping("/alterarProducao/{id}")
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
    @DeleteMapping("/excluirProducao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!producaoBibliograficaRepository.existsById(id)) {
            throw new NoSuchElementException("Produção Bibliografica não encontrado com id: " + id);
        }
        producaoBibliograficaRepository.deleteById(id);
    }
}
