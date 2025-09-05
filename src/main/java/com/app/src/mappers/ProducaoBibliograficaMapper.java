package com.app.src.mappers;

import com.app.src.dto.ProducaoBibliograficaDTO;
import com.app.src.models.ProducaoBibliografica;

public class ProducaoBibliograficaMapper {
    public static ProducaoBibliograficaDTO toDTO(ProducaoBibliografica producaoBibliografica) {
        if (producaoBibliografica == null) {
            return null;
        }

        ProducaoBibliograficaDTO dto = new ProducaoBibliograficaDTO();
        dto.setId(producaoBibliografica.getId());
        dto.setPesquisador(producaoBibliografica.getPesquisador());
        dto.setTipo(producaoBibliografica.getTipo());
        dto.setTitulo(producaoBibliografica.getTitulo());
        dto.setAno(producaoBibliografica.getAno());
        dto.setVeiculoPublicacao(producaoBibliografica.getVeiculoPublicacao());
        dto.setIssn(producaoBibliografica.getIssn());
        dto.setDoi(producaoBibliografica.getDoi());
        dto.setAutores(producaoBibliografica.getAutores());
        dto.setDestaque(producaoBibliografica.getDestaque());
        return dto;
    }

    public static ProducaoBibliografica toEntity(ProducaoBibliograficaDTO dto) {
        if (dto == null) {
            return null;
        }

        ProducaoBibliografica producaoBibliografica = new ProducaoBibliografica();
        producaoBibliografica.setPesquisador(dto.getPesquisador());
        producaoBibliografica.setTipo(dto.getTipo());
        producaoBibliografica.setTitulo(dto.getTitulo());
        producaoBibliografica.setAno(dto.getAno());
        producaoBibliografica.setVeiculoPublicacao(dto.getVeiculoPublicacao());
        producaoBibliografica.setIssn(dto.getIssn());
        producaoBibliografica.setDoi(dto.getDoi());
        producaoBibliografica.setAutores(dto.getAutores());
        producaoBibliografica.setDestaque(dto.getDestaque());
        return producaoBibliografica;
    }
}
