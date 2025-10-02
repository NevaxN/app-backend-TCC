package com.app.src.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.EventoDTO;
import com.app.src.mappers.EventoMapper;
import com.app.src.models.Evento;
import com.app.src.repositories.EventoRepository;
import com.app.src.repositories.PesquisadorRepository;

@Service
public class EventoService extends GenericCrudService<Evento, EventoDTO, Integer, EventoRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;
    
    public EventoService(EventoRepository repository, EventoMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "eventos", key = "#id")
    public EventoDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public EventoDTO salvar(EventoDTO eventoDTO){
        Evento evento = mapper.toEntity(eventoDTO);

        if (evento.getPesquisador() == null || evento.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(evento.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + evento.getPesquisador().getId());
        }

        return super.salvar(eventoDTO);
    }

    public EventoDTO atualizar(Integer id, EventoDTO dadosAtualizados){
        Evento existente = repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pesquisador não encontrado com id: " + id));

        ((EventoMapper) mapper).updateEntityFromDto(dadosAtualizados, existente);

        Evento salvo = repository.save(existente);

        return mapper.toDTO(salvo);
    }
}
