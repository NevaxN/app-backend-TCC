package com.app.src.mappers;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.models.FormacaoAcademica;

public class FormacaoAcademicaMapper {
        public static FormacaoAcademicaDTO toDTO(FormacaoAcademica graduacao) {
        if (graduacao == null) {
            return null;
        }

        FormacaoAcademicaDTO dto = new FormacaoAcademicaDTO();
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
        dto.setSequenciaFormacao(graduacao.getSequenciaFormacao());
        return dto;
    }

    public static FormacaoAcademica toEntity(FormacaoAcademicaDTO dto) {
        if (dto == null) {
            return null;
        }

        FormacaoAcademica graduacao = new FormacaoAcademica();
        graduacao.setPesquisador(dto.getPesquisador());
        graduacao.setInstituicao(dto.getInstituicao());
        graduacao.setCurso(dto.getCurso());
        graduacao.setStatus(dto.getStatus());
        graduacao.setAnoInicio(dto.getAnoInicio());
        graduacao.setAnoConclusao(dto.getAnoConclusao());
        graduacao.setTituloTrabalho(dto.getTituloTrabalho());
        graduacao.setOrientador(dto.getOrientador());
        graduacao.setDestaque(dto.getDestaque());
        graduacao.setSequenciaFormacao(dto.getSequenciaFormacao());
        return graduacao;
    }
}
