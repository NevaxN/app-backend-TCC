package com.app.src.dto.perfilAcademico;

import com.app.src.dto.*;

public record AlteracaoPerfilAcademicoDTO (
    ListaPerfilAcademicoDTO<FormacaoAcademicaSemPesquisadorDTO, FormacaoAcademicaDTO> formacoesAcademicas,
    ListaPerfilAcademicoDTO <AtuacaoProfissionalSemPesquisadorDTO, AtuacaoProfissionalDTO> atuacoesProfissionais,
    ListaPerfilAcademicoDTO <ArtigoSemPesquisadorDTO, ArtigoDTO> artigos,
    ListaPerfilAcademicoDTO <LivroSemPesquisadorDTO, LivroDTO> livros,
    ListaPerfilAcademicoDTO <CapituloSemPesquisadorDTO, CapituloDTO> capitulos,
    ListaPerfilAcademicoDTO <OrientacoesSemPesquisadorDTO, OrientacaoDTO> orientacoes,
    ListaPerfilAcademicoDTO <ProjetoPesquisaSemPesquisadorDTO, ProjetoPesquisaDTO> projetosPesquisa,
    ListaPerfilAcademicoDTO <PremiacaoSemPesquisadorDTO, PremiacaoDTO> premiacoes,
    ListaPerfilAcademicoDTO <TrabalhoEventoSemPesquisadorDTO, TrabalhoEventoDTO> trabalhosEventos
) {
}
