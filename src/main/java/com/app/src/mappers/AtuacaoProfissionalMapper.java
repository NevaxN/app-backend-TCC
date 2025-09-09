package com.app.src.mappers;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.models.AtuacaoProfissional;

public class AtuacaoProfissionalMapper {
    public static AtuacaoProfissionalDTO toDTO(AtuacaoProfissional atuacaoProfissional) {
        if (atuacaoProfissional == null) {
            return null;
        }

        AtuacaoProfissionalDTO dto = new AtuacaoProfissionalDTO();
        dto.setId(atuacaoProfissional.getId());
        dto.setPesquisador(atuacaoProfissional.getPesquisador());
        dto.setInstituicao(atuacaoProfissional.getInstituicao());
        dto.setCargo(atuacaoProfissional.getCargo());
        dto.setAnoInicio(atuacaoProfissional.getAnoInicio());
        dto.setAnoFim(atuacaoProfissional.getAnoFim());
        dto.setDestaque(atuacaoProfissional.getDestaque());
        dto.setMesFim(atuacaoProfissional.getMesFim());
        dto.setMesInicio(atuacaoProfissional.getMesInicio());
        dto.setSequenciaAtuacao(atuacaoProfissional.getSequenciaAtuacao());
        dto.setSequenciaVinculo(atuacaoProfissional.getSequenciaVinculo());
        return dto;
    }

    public static AtuacaoProfissional toEntity(AtuacaoProfissionalDTO dto) {
        if (dto == null) {
            return null;
        }

        AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();
        atuacaoProfissional.setPesquisador(dto.getPesquisador());
        atuacaoProfissional.setInstituicao(dto.getInstituicao());
        atuacaoProfissional.setCargo(dto.getCargo());
        atuacaoProfissional.setAnoInicio(dto.getAnoInicio());
        atuacaoProfissional.setAnoFim(dto.getAnoFim());
        atuacaoProfissional.setDestaque(dto.getDestaque());
        atuacaoProfissional.setMesInicio(dto.getMesInicio());
        atuacaoProfissional.setMesFim(dto.getMesFim());
        atuacaoProfissional.setSequenciaAtuacao(dto.getSequenciaAtuacao());
        atuacaoProfissional.setSequenciaVinculo(dto.getSequenciaVinculo());
        return atuacaoProfissional;
    }
}
