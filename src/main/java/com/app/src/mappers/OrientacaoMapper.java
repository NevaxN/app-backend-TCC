package com.app.src.mappers;

import com.app.src.dto.OrientacaoDTO;
import com.app.src.models.Orientacao;

public class OrientacaoMapper {
    
    public static OrientacaoDTO toDTO(Orientacao orientacao) {
        if (orientacao == null) {
            return null;
        }

        OrientacaoDTO dto = new OrientacaoDTO();
        dto.setId(orientacao.getId());
        dto.setPesquisador(orientacao.getPesquisador());
        dto.setTipo(orientacao.getTipo());
        dto.setNomeOrientado(orientacao.getNomeOrientado());
        dto.setTituloTrabalho(orientacao.getTituloTrabalho());
        dto.setInstituicao(orientacao.getInstituicao());
        dto.setAnoInicio(orientacao.getAnoInicio());
        dto.setAnoFim(orientacao.getAnoFim());
        dto.setDestaque(orientacao.getDestaque());
        return dto;
    }

    public static Orientacao toEntity(OrientacaoDTO dto) {
        if (dto == null) {
            return null;
        }

        Orientacao orientacao = new Orientacao();
        orientacao.setPesquisador(dto.getPesquisador());
        orientacao.setTipo(dto.getTipo());
        orientacao.setNomeOrientado(dto.getNomeOrientado());
        orientacao.setTituloTrabalho(dto.getTituloTrabalho());
        orientacao.setInstituicao(dto.getInstituicao());
        orientacao.setAnoInicio(dto.getAnoInicio());
        orientacao.setAnoFim(dto.getAnoFim());
        orientacao.setDestaque(dto.getDestaque());
        return orientacao;
    }
}
