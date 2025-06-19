package com.app.src.controller;

import com.app.src.model.Evento;
import com.app.src.repository.EventoRepository;
import com.app.src.repository.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Evento buscarPorId(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(evento.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + evento.getPesquisador().getId());
        }
        return eventoRepository.save(evento);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Evento atualizar(@PathVariable Integer id, @RequestBody Evento eventoAtualizada) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com id: " + id));

        evento.setNomeEvento(eventoAtualizada.getNomeEvento());
        evento.setTipo(eventoAtualizada.getTipo());
        evento.setTituloTrabalho(eventoAtualizada.getTituloTrabalho());
        evento.setAno(eventoAtualizada.getAno());
        evento.setLocal(eventoAtualizada.getLocal());

        return eventoRepository.save(evento);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!eventoRepository.existsById(id)) {
            throw new NoSuchElementException("Evento não encontrado com id: " + id);
        }
        eventoRepository.deleteById(id);
    }
}
