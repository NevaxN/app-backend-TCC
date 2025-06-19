package com.app.src.controller;

import com.app.src.model.Favorito;
import com.app.src.dto.FavoritoDTO;
import com.app.src.mapper.FavoritoMapper;
import com.app.src.repository.FavoritoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
        
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarFavoritos")
    public List<FavoritoDTO> listarTodos() {
        return favoritoRepository.findAll().stream()
                .map(FavoritoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/listarFavorito/{id}")
    public FavoritoDTO buscarPorId(@PathVariable Integer id) {
        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Favorito não encontrado com id: " + id));
            
        return FavoritoMapper.toDTO(favorito);
    }

    @PostMapping("/salvarFavorito")
    public FavoritoDTO criar(@RequestBody FavoritoDTO favoritoDTO) {
        Favorito favorito = FavoritoMapper.toEntity(favoritoDTO);

        if (favorito.getPesquisador() == null || favorito.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(favorito.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + favorito.getPesquisador().getId());
        }

        Favorito salvo = favoritoRepository.save(favorito);
        
        return FavoritoMapper.toDTO(salvo);
    }

    @DeleteMapping("/excluirFavorito/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!favoritoRepository.existsById(id)) {
            throw new NoSuchElementException("Favorito não encontrado com id: " + id);
        }
        favoritoRepository.deleteById(id);
    }
}
