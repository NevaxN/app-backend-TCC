package com.app.src.mapper;

import com.app.src.dto.IdiomaDTO;
import com.app.src.model.Idioma;

public class IdiomaMapper {
    public static IdiomaDTO toDTO(Idioma idioma) {
        if (idioma == null) {
            return null;
        }

        IdiomaDTO dto = new IdiomaDTO();
        dto.setId(idioma.getId());
        dto.setPesquisador(idioma.getPesquisador());
        dto.setIdioma(idioma.getIdioma());
        dto.setLeitura(idioma.getLeitura());
        dto.setEscrita(idioma.getEscrita());
        dto.setFala(idioma.getFala());
        return dto;
    }

    public static Idioma toEntity(IdiomaDTO dto) {
        if (dto == null) {
            return null;
        }

        Idioma idioma = new Idioma();
        idioma.setPesquisador(dto.getPesquisador());
        idioma.setIdioma(dto.getIdioma());
        idioma.setLeitura(dto.getLeitura());
        idioma.setEscrita(dto.getEscrita());
        idioma.setFala(dto.getFala());
        return idioma;
    }
}
