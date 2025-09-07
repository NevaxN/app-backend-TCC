package com.app.src.controller;

import com.app.src.dto.CapituloDTO;
import com.app.src.mapper.CapituloMapper;
import com.app.src.model.Capitulo;
import com.app.src.repository.CapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/capitulos")
public class CapituloController {

    @Autowired
    private CapituloRepository capituloRepository;

    // Listar todos os capítulos
    @GetMapping("/listarCapitulos")
    public List<CapituloDTO> listarTodos() {
        return capituloRepository.findAll().stream()
                .map(CapituloMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar capítulo por ID
    @GetMapping("/listarCapitulo/{id}")
    public CapituloDTO buscarPorId(@PathVariable Integer id) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Capítulo não encontrado com id: " + id));

        return CapituloMapper.toDTO(capitulo);
    }

    // Criar novo capítulo
    @PostMapping("/salvarCapitulo")
    public CapituloDTO criar(@RequestBody CapituloDTO capituloDTO) {
        Capitulo capitulo = CapituloMapper.toEntity(capituloDTO);

        Capitulo salvo = capituloRepository.save(capitulo);

        return CapituloMapper.toDTO(salvo);
    }

    // Atualizar capítulo
    @PutMapping("/alterarCapitulo/{id}")
    public CapituloDTO atualizar(@PathVariable Integer id, @RequestBody Capitulo dadosAtualizados) {
        Capitulo existente = capituloRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Capítulo não encontrado com id: " + id));

        existente.setSequenciaProducao(dadosAtualizados.getSequenciaProducao());
        existente.setPesquisador(dadosAtualizados.getPesquisador());
        existente.setAutores(dadosAtualizados.getAutores());
        existente.setAno(dadosAtualizados.getAno());
        existente.setDestaque(dadosAtualizados.getDestaque());
        existente.setTituloCapitulo(dadosAtualizados.getTituloCapitulo());
        existente.setNomeLivro(dadosAtualizados.getNomeLivro());
        existente.setEditora(dadosAtualizados.getEditora());
        existente.setIdioma(dadosAtualizados.getIdioma());
        existente.setDoi(dadosAtualizados.getDoi());
        existente.setPaginaInicial(dadosAtualizados.getPaginaInicial());
        existente.setPaginaFinal(dadosAtualizados.getPaginaFinal());

        Capitulo salvo = capituloRepository.save(existente);

        return CapituloMapper.toDTO(salvo);
    }

    // Deletar capítulo
    @DeleteMapping("/excluirCapitulo/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!capituloRepository.existsById(id)) {
            throw new NoSuchElementException("Capítulo não encontrado com id: " + id);
        }
        capituloRepository.deleteById(id);
    }
}
