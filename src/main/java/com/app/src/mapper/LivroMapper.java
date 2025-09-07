package com.app.src.mapper;

import com.app.src.dto.LivroDTO;
import com.app.src.model.Livro;

public class LivroMapper {

    public static LivroDTO toDTO(Livro livro) {
        if (livro == null) {
            return null;
        }

        return new LivroDTO(
                livro.getSequenciaProducao(),
                livro.getPesquisador(),
                livro.getAutores(),
                livro.getIsbn(),
                livro.getEditora(),
                livro.getAno(),
                livro.getNumeroPaginas(),
                livro.getDestaque(),
                livro.getId(),
                livro.getIdioma(),
                livro.getTitulo()
        );
    }

    public static Livro toEntity(LivroDTO dto) {
        if (dto == null) {
            return null;
        }

        Livro livro = new Livro();
        livro.setSequenciaProducao(dto.sequenciaProducao());
        livro.setPesquisador(dto.pesquisador());
        livro.setAutores(dto.autores());
        livro.setIsbn(dto.isbn());
        livro.setEditora(dto.editora());
        livro.setAno(dto.ano());
        livro.setNumeroPaginas(dto.numeroPaginas());
        livro.setDestaque(dto.destaque());
        livro.setId(dto.id());
        livro.setIdioma(dto.idioma());
        livro.setTitulo(dto.titulo());

        return livro;
    }
}
