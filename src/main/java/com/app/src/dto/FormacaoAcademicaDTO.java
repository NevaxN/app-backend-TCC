package com.app.src.dto;

public record FormacaoAcademicaDTO(
        Integer id,
        PesquisadorDTO pesquisador,
        String nivel,
        int sequenciaFormacao,
        String instituicao,
        String curso,
        String status,
        Integer anoInicio,
        Integer anoConclusao,
        String tituloTrabalho,
        String orientador,
        Boolean destaque
) {}

