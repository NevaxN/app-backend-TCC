package com.app.src.controller;

import com.app.src.dto.ArtigoDTO;
import com.app.src.mapper.ArtigoMapper;
import com.app.src.model.Artigo;
import com.app.src.repository.ArtigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/artigos")
public class ArtigoController {

    @Autowired
    private ArtigoRepository artigoRepository;

    // Listar todos os artigos
    @GetMapping("/listarArtigos")
    public List<ArtigoDTO> listarTodos() {
        return artigoRepository.findAll().stream()
                .map(ArtigoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar artigo por ID
    @GetMapping("/listarArtigo/{id}")
    public ArtigoDTO buscarPorId(@PathVariable Integer id) {
        Artigo artigo = artigoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Artigo não encontrado com id: " + id));

        return ArtigoMapper.toDTO(artigo);
    }

    // Criar novo artigo
    @PostMapping("/salvarArtigo")
    public ArtigoDTO criar(@RequestBody ArtigoDTO artigoDTO) {
        Artigo artigo = ArtigoMapper.toEntity(artigoDTO);

        Artigo salvo = artigoRepository.save(artigo);

        return ArtigoMapper.toDTO(salvo);
    }

    // Atualizar artigo
    @PutMapping("/alterarArtigo/{id}")
    public ArtigoDTO atualizar(@PathVariable Integer id, @RequestBody Artigo dadosAtualizados) {
        Artigo existente = artigoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Artigo não encontrado com id: " + id));

        existente.setSequenciaProducao(dadosAtualizados.getSequenciaProducao());
        existente.setPesquisador(dadosAtualizados.getPesquisador());
        existente.setAutores(dadosAtualizados.getAutores());
        existente.setAno(dadosAtualizados.getAno());
        existente.setDestaque(dadosAtualizados.getDestaque());
        existente.setTitulo(dadosAtualizados.getTitulo());
        existente.setPeriodico(dadosAtualizados.getPeriodico());
        existente.setDoi(dadosAtualizados.getDoi());
        existente.setIdioma(dadosAtualizados.getIdioma());

        Artigo salvo = artigoRepository.save(existente);

        return ArtigoMapper.toDTO(salvo);
    }

    // Deletar artigo
    @DeleteMapping("/excluirArtigo/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!artigoRepository.existsById(id)) {
            throw new NoSuchElementException("Artigo não encontrado com id: " + id);
        }
        artigoRepository.deleteById(id);
    }
}
