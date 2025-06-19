package com.app.src.mapper;

import com.app.src.dto.SeguidorDTO;
import com.app.src.model.Seguidor;

public class SeguidorMapper {
    public static SeguidorDTO toDTO(Seguidor seguidor) {
        if (seguidor == null) {
            return null;
        }

        SeguidorDTO dto = new SeguidorDTO();
        dto.setId(seguidor.getId());
        dto.setPesquisador(seguidor.getPesquisador());
        return dto;
    }

    public static Seguidor toEntity(SeguidorDTO dto) {
        if (dto == null) {
            return null;
        }

        Seguidor seguidor = new Seguidor();
        seguidor.setPesquisador(dto.getPesquisador());
        return seguidor;
    }
}
