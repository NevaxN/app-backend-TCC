package com.app.src.mapper;

import com.app.src.dto.ArtigoDTO;
import com.app.src.model.Artigo;

public class ArtigoMapper {

    public static ArtigoDTO toDTO(Artigo artigo) {
        if (artigo == null) {
            return null;
        }

        return new ArtigoDTO(
                artigo.getSequenciaProducao(),
                artigo.getPesquisador(),
                artigo.getAutores(),
                artigo.getAno(),
                artigo.getDestaque(),
                artigo.getId(),
                artigo.getTitulo(),
                artigo.getPeriodico(),
                artigo.getDoi(),
                artigo.getIdioma()
        );
    }

    public static Artigo toEntity(ArtigoDTO dto) {
        if (dto == null) {
            return null;
        }

        Artigo artigo = new Artigo();
        artigo.setSequenciaProducao(dto.sequenciaProducao());
        artigo.setPesquisador(dto.pesquisador());
        artigo.setAutores(dto.autores());
        artigo.setAno(dto.ano());
        artigo.setDestaque(dto.destaque());
        artigo.setId(dto.id());
        artigo.setTitulo(dto.titulo());
        artigo.setPeriodico(dto.periodico());
        artigo.setDoi(dto.doi());
        artigo.setIdioma(dto.idioma());

        return artigo;
    }
}
