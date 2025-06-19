package com.app.src.mapper;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.model.AtuacaoProfissional;

public class AtuacaoProfissionalMapper {
    public static AtuacaoProfissionalDTO toDTO(AtuacaoProfissional atuacaoProfissional) {
        if (atuacaoProfissional == null) {
            return null;
        }

        AtuacaoProfissionalDTO dto = new AtuacaoProfissionalDTO();
        dto.setId(atuacaoProfissional.getId());
        dto.setPesquisador(atuacaoProfissional.getPesquisador());
        dto.setInstituicao(atuacaoProfissional.getInstituicao());
        dto.setVinculo(atuacaoProfissional.getVinculo());
        dto.setDepartamento(atuacaoProfissional.getDepartamento());
        dto.setCargo(atuacaoProfissional.getCargo());
        dto.setAnoInicio(atuacaoProfissional.getAnoInicio());
        dto.setAnoFim(atuacaoProfissional.getAnoFim());
        dto.setDestaque(atuacaoProfissional.getDestaque());
        return dto;
    }

    public static AtuacaoProfissional toEntity(AtuacaoProfissionalDTO dto) {
        if (dto == null) {
            return null;
        }

        AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();
        atuacaoProfissional.setPesquisador(dto.getPesquisador());
        atuacaoProfissional.setInstituicao(dto.getInstituicao());
        atuacaoProfissional.setVinculo(dto.getVinculo());
        atuacaoProfissional.setDepartamento(dto.getDepartamento());
        atuacaoProfissional.setCargo(dto.getCargo());
        atuacaoProfissional.setAnoInicio(dto.getAnoInicio());
        atuacaoProfissional.setAnoFim(dto.getAnoFim());
        atuacaoProfissional.setDestaque(dto.getDestaque());
        return atuacaoProfissional;
    }
}
