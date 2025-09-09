package com.app.src.mappers;

import com.app.src.dto.CapituloDTO;
import com.app.src.models.Capitulo;

public class CapituloMapper {

    public static CapituloDTO toDTO(Capitulo capitulo) {
        if (capitulo == null) {
            return null;
        }

        return new CapituloDTO(
                capitulo.getSequenciaProducao(),
                capitulo.getPesquisador(),
                capitulo.getAutores(),
                capitulo.getAno(),
                capitulo.getDestaque(),
                capitulo.getId(),
                capitulo.getTituloCapitulo(),
                capitulo.getNomeLivro(),
                capitulo.getEditora(),
                capitulo.getIdioma(),
                capitulo.getDoi(),
                capitulo.getPaginaInicial(),
                capitulo.getPaginaFinal()
        );
    }

    public static Capitulo toEntity(CapituloDTO dto) {
        if (dto == null) {
            return null;
        }

        Capitulo capitulo = new Capitulo();
        capitulo.setSequenciaProducao(dto.sequenciaProducao());
        capitulo.setPesquisador(dto.pesquisador());
        capitulo.setAutores(dto.autores());
        capitulo.setAno(dto.ano());
        capitulo.setDestaque(dto.destaque());
        capitulo.setId(dto.id());
        capitulo.setTituloCapitulo(dto.tituloCapitulo());
        capitulo.setNomeLivro(dto.nomeLivro());
        capitulo.setEditora(dto.editora());
        capitulo.setIdioma(dto.idioma());
        capitulo.setDoi(dto.doi());
        capitulo.setPaginaInicial(dto.paginaInicial());
        capitulo.setPaginaFinal(dto.paginaFinal());

        return capitulo;
    }
}
