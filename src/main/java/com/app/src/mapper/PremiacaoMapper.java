package com.app.src.mapper;

import com.app.src.dto.PremiacaoDTO;
import com.app.src.model.Premiacao;

public class PremiacaoMapper {
    public static PremiacaoDTO toDTO(Premiacao premiacao) {
        if (premiacao == null) {
            return null;
        }

        PremiacaoDTO dto = new PremiacaoDTO();
        dto.setId(premiacao.getId());
        dto.setPesquisador(premiacao.getPesquisador());
        dto.setTitulo(premiacao.getTitulo());
        dto.setInstituicao(premiacao.getInstituicao());
        dto.setAno(premiacao.getAno());
        return dto;
    }

    public static Premiacao toEntity(PremiacaoDTO dto) {
        if (dto == null) {
            return null;
        }

        Premiacao premiacao = new Premiacao();
        premiacao.setPesquisador(dto.getPesquisador());
        premiacao.setTitulo(dto.getTitulo());
        premiacao.setInstituicao(dto.getInstituicao());
        premiacao.setAno(dto.getAno());
        return premiacao;
    }
}
