package com.app.src.mappers;

import com.app.src.dto.FavoritoDTO;
import com.app.src.models.Favorito;

public class FavoritoMapper {
        public static FavoritoDTO toDTO(Favorito favorito) {
        if (favorito == null) {
            return null;
        }

        FavoritoDTO dto = new FavoritoDTO();
        dto.setId(favorito.getId());
        dto.setPesquisador(favorito.getPesquisador());
        return dto;
    }

    public static Favorito toEntity(FavoritoDTO dto) {
        if (dto == null) {
            return null;
        }

        Favorito favorito = new Favorito();
        favorito.setPesquisador(dto.getPesquisador());
        return favorito;
    }
}
