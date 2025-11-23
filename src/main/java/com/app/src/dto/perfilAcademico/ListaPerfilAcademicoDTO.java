package com.app.src.dto.perfilAcademico;

import java.util.List;

public record ListaPerfilAcademicoDTO<T, Y> (
        List<T> adicionados,
        List<Y> editados,
        List<Integer> deletados
) {
}
