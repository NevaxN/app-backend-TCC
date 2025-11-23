package com.app.src.dto.perfilAcademico;

public record FormacaoAcademicaSemPesquisadorDTO(
        Integer id,
        String nivel,
        String instituicao,
        String curso,
        String status,
        Integer anoInicio,
        Integer anoConclusao,
        String tituloTrabalho,
        String orientador,
        Boolean destaque
) {}
