package com.app.src.mapper;

import com.app.src.dto.EventoDTO;
import com.app.src.model.Evento;

public class EventoMapper {
        public static EventoDTO toDTO(Evento evento) {
        if (evento == null) {
            return null;
        }

        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setPesquisador(evento.getPesquisador());
        dto.setNomeEvento(evento.getNomeEvento());
        dto.setTipo(evento.getTipo());
        dto.setTituloTrabalho(evento.getTituloTrabalho());
        dto.setAno(evento.getAno());
        dto.setLocal(evento.getLocal());
        return dto;
    }

    public static Evento toEntity(EventoDTO dto) {
        if (dto == null) {
            return null;
        }

        Evento evento = new Evento();
        evento.setPesquisador(dto.getPesquisador());
        evento.setNomeEvento(dto.getNomeEvento());
        evento.setTipo(dto.getTipo());
        evento.setTituloTrabalho(dto.getTituloTrabalho());
        evento.setAno(dto.getAno());
        evento.setLocal(evento.getLocal());
        return evento;
    }
}
