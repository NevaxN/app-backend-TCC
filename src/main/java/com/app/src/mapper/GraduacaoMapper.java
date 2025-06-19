package com.app.src.mapper;

import com.app.src.dto.GraduacaoDTO;
import com.app.src.model.Graduacao;

public class GraduacaoMapper {
        public static GraduacaoDTO toDTO(Graduacao graduacao) {
        if (graduacao == null) {
            return null;
        }

        GraduacaoDTO dto = new GraduacaoDTO();
        dto.setId(graduacao.getId());
        dto.setPesquisador(graduacao.getPesquisador());
        dto.setInstituicao(graduacao.getInstituicao());
        dto.setCurso(graduacao.getCurso());
        dto.setStatus(graduacao.getStatus());
        dto.setAnoInicio(graduacao.getAnoInicio());
        dto.setAnoConclusao(graduacao.getAnoConclusao());
        dto.setTituloTrabalho(graduacao.getTituloTrabalho());
        dto.setOrientador(graduacao.getOrientador());
        dto.setDestaque(graduacao.getDestaque());
        return dto;
    }

    public static Graduacao toEntity(GraduacaoDTO dto) {
        if (dto == null) {
            return null;
        }

        Graduacao graduacao = new Graduacao();
        graduacao.setPesquisador(dto.getPesquisador());
        graduacao.setInstituicao(dto.getInstituicao());
        graduacao.setCurso(dto.getCurso());
        graduacao.setStatus(dto.getStatus());
        graduacao.setAnoInicio(dto.getAnoInicio());
        graduacao.setAnoConclusao(dto.getAnoConclusao());
        graduacao.setTituloTrabalho(dto.getTituloTrabalho());
        graduacao.setOrientador(dto.getOrientador());
        graduacao.setDestaque(dto.getDestaque());
        return graduacao;
    }
}
