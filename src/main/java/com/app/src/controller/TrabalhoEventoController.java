package com.app.src.controller;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.mapper.TrabalhoEventoMapper;
import com.app.src.model.TrabalhoEvento;
import com.app.src.repository.TrabalhoEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trabalhos-evento")
public class TrabalhoEventoController {

    @Autowired
    private TrabalhoEventoRepository trabalhoEventoRepository;

    // Listar todos os trabalhos em eventos
    @GetMapping("/listarTrabalhosEvento")
    public List<TrabalhoEventoDTO> listarTodos() {
        return trabalhoEventoRepository.findAll().stream()
                .map(TrabalhoEventoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar trabalho por ID
    @GetMapping("/listarTrabalhoEvento/{id}")
    public TrabalhoEventoDTO buscarPorId(@PathVariable Integer id) {
        TrabalhoEvento trabalhoEvento = trabalhoEventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trabalho de evento não encontrado com id: " + id));

        return TrabalhoEventoMapper.toDTO(trabalhoEvento);
    }

    // Criar novo trabalho de evento
    @PostMapping("/salvarTrabalhoEvento")
    public TrabalhoEventoDTO criar(@RequestBody TrabalhoEventoDTO trabalhoEventoDTO) {
        TrabalhoEvento trabalhoEvento = TrabalhoEventoMapper.toEntity(trabalhoEventoDTO);

        TrabalhoEvento salvo = trabalhoEventoRepository.save(trabalhoEvento);

        return TrabalhoEventoMapper.toDTO(salvo);
    }

    // Atualizar trabalho de evento
    @PutMapping("/alterarTrabalhoEvento/{id}")
    public TrabalhoEventoDTO atualizar(@PathVariable Integer id, @RequestBody TrabalhoEvento dadosAtualizados) {
        TrabalhoEvento existente = trabalhoEventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trabalho de evento não encontrado com id: " + id));

        existente.setSequenciaProducao(dadosAtualizados.getSequenciaProducao());
        existente.setPesquisador(dadosAtualizados.getPesquisador());
        existente.setAutores(dadosAtualizados.getAutores());
        existente.setAno(dadosAtualizados.getAno());
        existente.setDestaque(dadosAtualizados.getDestaque());
        existente.setTitulo(dadosAtualizados.getTitulo());
        existente.setClassificacaoEvento(dadosAtualizados.getClassificacaoEvento());
        existente.setNomeEvento(dadosAtualizados.getNomeEvento());
        existente.setCidadeEvento(dadosAtualizados.getCidadeEvento());

        TrabalhoEvento salvo = trabalhoEventoRepository.save(existente);

        return TrabalhoEventoMapper.toDTO(salvo);
    }

    // Deletar trabalho de evento
    @DeleteMapping("/excluirTrabalhoEvento/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!trabalhoEventoRepository.existsById(id)) {
            throw new NoSuchElementException("Trabalho de evento não encontrado com id: " + id);
        }
        trabalhoEventoRepository.deleteById(id);
    }
}
