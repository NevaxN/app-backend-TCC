package com.app.src.mappers;

import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.models.ProjetoPesquisa;

public class ProjetoPesquisaMapper {
    public static ProjetoPesquisaDTO toDTO(ProjetoPesquisa projetoPesquisa) {
        if (projetoPesquisa == null) {
            return null;
        }

        ProjetoPesquisaDTO dto = new ProjetoPesquisaDTO();
        dto.setId(projetoPesquisa.getId());
        dto.setPesquisador(projetoPesquisa.getPesquisador());
        dto.setTitulo(projetoPesquisa.getTitulo());
        dto.setDescricao(projetoPesquisa.getDescricao());
        dto.setInstituicao(projetoPesquisa.getInstituicao());
        dto.setAnoInicio(projetoPesquisa.getAnoInicio());
        dto.setAnoFim(projetoPesquisa.getAnoFim());
        dto.setFinanciador(projetoPesquisa.getFinanciador());
        dto.setDestaque(projetoPesquisa.getDestaque());
        return dto;
    }

    public static ProjetoPesquisa toEntity(ProjetoPesquisaDTO dto) {
        if (dto == null) {
            return null;
        }

        ProjetoPesquisa projetoPesquisa = new ProjetoPesquisa();
        projetoPesquisa.setPesquisador(dto.getPesquisador());
        projetoPesquisa.setTitulo(dto.getTitulo());
        projetoPesquisa.setDescricao(dto.getDescricao());
        projetoPesquisa.setInstituicao(dto.getInstituicao());
        projetoPesquisa.setAnoInicio(dto.getAnoInicio());
        projetoPesquisa.setAnoFim(dto.getAnoFim());
        projetoPesquisa.setFinanciador(dto.getFinanciador());
        projetoPesquisa.setDestaque(dto.getDestaque());
        return projetoPesquisa;
    }
}
