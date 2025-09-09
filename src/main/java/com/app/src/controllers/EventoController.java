package com.app.src.controllers;

import com.app.src.dto.EventoDTO;
import com.app.src.mappers.EventoMapper;
import com.app.src.models.Evento;
import com.app.src.repositories.EventoRepository;
import com.app.src.repositories.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarEventos")
    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(EventoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEvento/{id}")
    public EventoDTO buscarPorId(@PathVariable Integer id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com id: " + id));

        return EventoMapper.toDTO(evento);
    }

    // Criar novo endereço
    @PostMapping("/salvarEvento")
    public EventoDTO criar(@RequestBody EventoDTO eventoDTO) {
        Evento evento = EventoMapper.toEntity(eventoDTO);

        if (evento.getPesquisador() == null || evento.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(evento.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + evento.getPesquisador().getId());
        }

        Evento salvo = eventoRepository.save(evento);

        return EventoMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarEvento/{id}")
    public EventoDTO atualizar(@PathVariable Integer id, @RequestBody Evento eventoAtualizada) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com id: " + id));

        evento.setNomeEvento(eventoAtualizada.getNomeEvento());
        evento.setTipo(eventoAtualizada.getTipo());
        evento.setTituloTrabalho(eventoAtualizada.getTituloTrabalho());
        evento.setAno(eventoAtualizada.getAno());
        evento.setLocal(eventoAtualizada.getLocal());

        Evento salvo = eventoRepository.save(evento);

        return EventoMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirEvento/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!eventoRepository.existsById(id)) {
            throw new NoSuchElementException("Evento não encontrado com id: " + id);
        }
        eventoRepository.deleteById(id);
    }
}
