package com.app.src.services;

import com.app.src.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DadosPesquisadorService {

    private final PesquisadorService pesquisadorService;
    private final FormacaoAcademicaService formacaoAcademicaService;
    private final IdiomaService idiomaService;
    private final AtuacaoProfissionalService atuacaoProfissionalService;
    private final ArtigoService artigoService;
    private final LivroService livroService;
    private final CapituloService capituloService;
    private final ProjetoPesquisaService projetoPesquisaService;
    private final TrabalhoEventoService trabalhoEventoService;
    private final TagService tagService;

    public DadosPesquisadorService(PesquisadorService pesquisadorService, FormacaoAcademicaService formacaoAcademicaService, IdiomaService idiomaService, AtuacaoProfissionalService atuacaoProfissionalService, ArtigoService artigoService, LivroService livroService, CapituloService capituloService, ProjetoPesquisaService projetoPesquisaService, TrabalhoEventoService trabalhoEventoService, TagService tagService) {
        this.pesquisadorService = pesquisadorService;
        this.formacaoAcademicaService = formacaoAcademicaService;
        this.idiomaService = idiomaService;
        this.atuacaoProfissionalService = atuacaoProfissionalService;
        this.artigoService = artigoService;
        this.livroService = livroService;
        this.capituloService = capituloService;
        this.projetoPesquisaService = projetoPesquisaService;
        this.trabalhoEventoService = trabalhoEventoService;
        this.tagService = tagService;
    }

    public DadosPesquisadorDTO buscarDadosPesquisadorPorId(Integer idPesquisador) {

        PesquisadorDTO pesquisador = pesquisadorService.buscarPorId(idPesquisador);
        List<FormacaoAcademicaDTO> formacoesAcademicas = formacaoAcademicaService.buscarPorIdPesquisador(idPesquisador);
        List<IdiomaDTO> idiomas = idiomaService.buscarPorIdPesquisador(idPesquisador);
        List<AtuacaoProfissionalDTO> atuacoesProfissionais = atuacaoProfissionalService.buscarPorIdPesquisador(idPesquisador);
        List<ProjetoPesquisaDTO> projetoPesquisas = projetoPesquisaService.buscarPorIdPesquisador(idPesquisador);
        List<ArtigoDTO> artigos = artigoService.buscarPorIdPesquisador(idPesquisador);
        List<LivroDTO> livros = livroService.buscarPorIdPesquisador(idPesquisador);
        List<CapituloDTO> capitulos = capituloService.buscarPorIdPesquisador(idPesquisador);
        List<TrabalhoEventoDTO> trabalhosEventos = trabalhoEventoService.buscarPorIdPesquisador(idPesquisador);
        TagDTO tags = tagService.buscarPorIdPesquisador(idPesquisador);


        return new DadosPesquisadorDTO(
                pesquisador,
                formacoesAcademicas,
                idiomas,
                atuacoesProfissionais,
                artigos,
                livros,
                capitulos,
                trabalhosEventos,
                projetoPesquisas,
                tags
        );
    }

}
