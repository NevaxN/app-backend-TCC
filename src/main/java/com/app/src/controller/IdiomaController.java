package com.app.src.controller;

import com.app.src.model.Idioma;
import com.app.src.dto.IdiomaDTO;
import com.app.src.mapper.IdiomaMapper;
import com.app.src.repository.IdiomaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/idiomas")
public class IdiomaController {
    
    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarIdiomas")
    public List<IdiomaDTO> listarTodos() {
        return idiomaRepository.findAll().stream()
                .map(IdiomaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarIdioma/{id}")
    public IdiomaDTO buscarPorId(@PathVariable Integer id) {
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Idioma não encontrado com id: " + id));

        return IdiomaMapper.toDTO(idioma);
    }

    // Criar novo endereço
    @PostMapping("/salvarIdioma")
    public IdiomaDTO criar(@RequestBody IdiomaDTO idiomaDTO) {
        Idioma idioma = IdiomaMapper.toEntity(idiomaDTO);
        
        if (idioma.getPesquisador() == null || idioma.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(idioma.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + idioma.getPesquisador().getId());
        }

        Idioma salvo = idiomaRepository.save(idioma);

        return IdiomaMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarIdioma/{id}")
    public IdiomaDTO atualizar(@PathVariable Integer id, @RequestBody Idioma idiomaAtualizada) {
        Idioma idioma = idiomaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Idioma não encontrado com id: " + id));

        idioma.setIdioma(idiomaAtualizada.getIdioma());
        idioma.setLeitura(idiomaAtualizada.getLeitura());
        idioma.setEscrita(idiomaAtualizada.getEscrita());
        idioma.setFala(idiomaAtualizada.getFala());

        Idioma salvo = idiomaRepository.save(idioma);

        return IdiomaMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirIdioma/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!idiomaRepository.existsById(id)) {
            throw new NoSuchElementException("Idioma não encontrado com id: " + id);
        }
        idiomaRepository.deleteById(id);
    }
}
