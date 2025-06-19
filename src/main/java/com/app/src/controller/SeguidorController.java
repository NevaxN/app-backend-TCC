package com.app.src.controller;

import com.app.src.model.Seguidor;
import com.app.src.dto.SeguidorDTO;
import com.app.src.mapper.SeguidorMapper;
import com.app.src.repository.SeguidorRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seguidores")
public class SeguidorController {
        
    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarSeguidores")
    public List<SeguidorDTO> listarTodos() {
        return seguidorRepository.findAll().stream()
                .map(SeguidorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/listarSeguidor/{id}")
    public SeguidorDTO buscarPorId(@PathVariable Integer id) {
        Seguidor seguidor = seguidorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seguidor não encontrado com id: " + id));
        return SeguidorMapper.toDTO(seguidor);
    }

    @PostMapping("/salvarSeguidor")
    public SeguidorDTO criar(@RequestBody SeguidorDTO seguidorDTO) {
        Seguidor seguidor = SeguidorMapper.toEntity(seguidorDTO);

        if(seguidor.getPesquisador() == null || seguidor.getPesquisador().getId() == null){
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(seguidor.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + seguidor.getPesquisador().getId());
        }

        Seguidor salvo = seguidorRepository.save(seguidor);
        return SeguidorMapper.toDTO(salvo);
    }

    @DeleteMapping("/excluirSeguidor/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!seguidorRepository.existsById(id)) {
            throw new NoSuchElementException("Seguidor não encontrado com id: " + id);
        }
        seguidorRepository.deleteById(id);
    }
}
