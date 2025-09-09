package com.app.src.mappers;

import com.app.src.dto.ListaDTO;
import com.app.src.models.Lista;

public class ListaMapper {
    public static ListaDTO toDTO(Lista lista) {
        if (lista == null) {
            return null;
        }

        ListaDTO dto = new ListaDTO();
        dto.setId(lista.getId());
        dto.setPesquisador(lista.getPesquisador());
        dto.setNomeLista(lista.getNomeLista());
        return dto;
    }

    public static Lista toEntity(ListaDTO dto) {
        if (dto == null) {
            return null;
        }

        Lista lista = new Lista();
        lista.setPesquisador(dto.getPesquisador());
        lista.setNomeLista(dto.getNomeLista());
        return lista;
    }
}
