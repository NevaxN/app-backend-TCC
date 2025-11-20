package com.app.src.dto;

import java.util.List;

public record DadosPesquisadorDTO(
        PesquisadorDTO pesquisador,
        UsuarioPreferenciasDTO preferencias,
        List<FormacaoAcademicaDTO> formacoesAcademicas,
        List<IdiomaDTO> idiomas,
        List<AtuacaoProfissionalDTO> atuacoesProfissionais,
        List<ArtigoDTO> artigos,
        List<LivroDTO> livros,
        List<CapituloDTO> capitulos,
        List<TrabalhoEventoDTO> trabalhosEvento,
        List<ProjetoPesquisaDTO> projetosPesquisa,
        List<PremiacaoDTO> premiacoes,
        List<OrientacaoDTO> orientacoes,
        TagDTO tags,
        List<LinhaTempoDTO> linhaDoTempo
) {
}
