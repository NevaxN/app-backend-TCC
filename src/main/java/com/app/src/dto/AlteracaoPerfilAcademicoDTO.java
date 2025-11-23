package com.app.src.dto;

public record AlteracaoPerfilAcademicoDTO (
    ListaPerfilAcademicoDTO <FormacaoAcademicaSemPesquisadorDTO, FormacaoAcademicaDTO> formacoesAcademicas,
    ListaPerfilAcademicoDTO <AtuacaoProfissionalSemPesquisadorDTO, AtuacaoProfissionalDTO> atuacoesProfissionais,
    ListaPerfilAcademicoDTO <ArtigoDTO, Object> artigos,
    ListaPerfilAcademicoDTO <LivroDTO, Object> livros,
    ListaPerfilAcademicoDTO <CapituloDTO, Object> capitulos,
    ListaPerfilAcademicoDTO <OrientacaoDTO, Object> orientacoes,
    ListaPerfilAcademicoDTO <ProjetoPesquisaDTO, Object> projetosPesquisa,
    ListaPerfilAcademicoDTO <PremiacaoDTO, Object> premiacoes,
    ListaPerfilAcademicoDTO <TrabalhoEventoDTO, Object> trabalhosEventos
) {
}
