package com.app.src.services;

import com.app.src.dto.*;
import com.app.src.models.Endereco;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    private final PremiacaoService premiacaoService;
    private final OrientacaoService orientacaoService;
    private final EnderecoService enderecoService;

    public DadosPesquisadorService(PesquisadorService pesquisadorService, FormacaoAcademicaService formacaoAcademicaService, IdiomaService idiomaService, AtuacaoProfissionalService atuacaoProfissionalService, ArtigoService artigoService, LivroService livroService, CapituloService capituloService, ProjetoPesquisaService projetoPesquisaService, TrabalhoEventoService trabalhoEventoService, TagService tagService, PremiacaoService premiacaoService, OrientacaoService orientacaoService, EnderecoService enderecoService) {
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
        this.premiacaoService = premiacaoService;
        this.orientacaoService = orientacaoService;
        this.enderecoService = enderecoService;
    }

    public DadosPesquisadorDTO buscarDadosPesquisadorPorId(Integer idPesquisador) {

        PesquisadorDTO pesquisador = pesquisadorService.buscarPorId(idPesquisador);

        // Endereço
        EnderecoDTO endereco = null;
        List<EnderecoDTO> enderecos = enderecoService.buscarPorIdPesquisador(idPesquisador);
        if (!enderecos.isEmpty()) {
            endereco = enderecos.getFirst();
        }

        // Inicializamos a linha do tempo vazia, e vamos preencher ela conforme buscamos os outros dados.
        List<LinhaTempoDTO> linhaDoTempo = new ArrayList<>();

        // Formação Acadêmica
        List<FormacaoAcademicaDTO> formacoesAcademicas =
                formacaoAcademicaService.buscarPorIdPesquisador(idPesquisador);

        formacoesAcademicas.stream()
                .filter(FormacaoAcademicaDTO::destaque)
                .map(f ->
                        new LinhaTempoDTO(
                                f.id(),
                                "Formação Acadêmica",
                                f.curso(),
                                f.anoInicio()
                        )
                )
                .forEach(linhaDoTempo::add);


        List<IdiomaDTO> idiomas =
                idiomaService.buscarPorIdPesquisador(idPesquisador);

        // Atuação Profissional
        List<AtuacaoProfissionalDTO> atuacoesProfissionais =
                atuacaoProfissionalService.buscarPorIdPesquisador(idPesquisador);

        atuacoesProfissionais.stream()
                .filter(AtuacaoProfissionalDTO::destaque)
                .map(a ->
                        new LinhaTempoDTO(
                                a.id(),
                                "Atuação Profissional",
                                a.cargo(),
                                a.anoInicio()
                        )
                )
                .forEach(linhaDoTempo::add);

        // Projeto de Pesquisa
        List<ProjetoPesquisaDTO> projetoPesquisas =
                projetoPesquisaService.buscarPorIdPesquisador(idPesquisador);

        projetoPesquisas.stream()
                .filter(ProjetoPesquisaDTO::destaque)
                .map(p ->
                        new LinhaTempoDTO(
                                p.id(),
                                "Projeto de Pesquisa",
                                p.titulo(),
                                p.ano()
                        )
                )
                .forEach(linhaDoTempo::add);

        // Artigo
        List<ArtigoDTO> artigos =
                artigoService.buscarPorIdPesquisador(idPesquisador);

        artigos.stream()
                .filter(ArtigoDTO::destaque)
                .map(a ->
                        new LinhaTempoDTO(
                                a.id(),
                                "Artigo",
                                a.titulo(),
                                a.ano()
                        )
                )
                .forEach(linhaDoTempo::add);


        // Livro
        List<LivroDTO> livros =
                livroService.buscarPorIdPesquisador(idPesquisador);

        livros.stream()
                .filter(LivroDTO::destaque)
                .map(l ->
                        new LinhaTempoDTO(
                                l.id(),
                                "Livro",
                                l.titulo(),
                                l.ano()
                        )
                )
                .forEach(linhaDoTempo::add);

        // Capítulo
        List<CapituloDTO> capitulos =
                capituloService.buscarPorIdPesquisador(idPesquisador);

        capitulos.stream()
                .filter(CapituloDTO::destaque)
                .map(c ->
                        new LinhaTempoDTO(
                                c.id(),
                                "Capítulo",
                                c.tituloCapitulo(),
                                c.ano()
                        )
                )
                .forEach(linhaDoTempo::add);


        // Trabalho em Evento
        List<TrabalhoEventoDTO> trabalhosEventos =
                trabalhoEventoService.buscarPorIdPesquisador(idPesquisador);

        trabalhosEventos.stream()
                .filter(TrabalhoEventoDTO::destaque)
                .map(t ->
                        new LinhaTempoDTO(
                                t.id(),
                                "Trabalho em Evento",
                                t.titulo(),
                                t.ano()
                        )
                )
                .forEach(linhaDoTempo::add);

        // Premiação
        List<PremiacaoDTO> premiacoes = premiacaoService.buscarPorIdPesquisador(idPesquisador);

        premiacoes.stream()
                .filter(PremiacaoDTO::destaque)
                .map(p ->
                        new LinhaTempoDTO(
                                p.id(),
                                "Premiação",
                                p.titulo(),
                                p.ano()
                        )
                )
                .forEach(linhaDoTempo::add);

        // Orientação
        List<OrientacaoDTO> orientacoes = orientacaoService.buscarPorIdPesquisador(idPesquisador);
        orientacoes.stream()
                .filter(OrientacaoDTO::destaque)
                .map(o ->
                        new LinhaTempoDTO(
                                o.id(),
                                "Orientação",
                                o.tituloTrabalho(),
                                o.ano()
                        )
                )
                .forEach(linhaDoTempo::add);

        TagDTO tags = tagService.buscarPorIdPesquisador(idPesquisador);

        List<LinhaTempoDTO> linhaDoTempoOrdenada = linhaDoTempo
                .stream()
                .sorted(Comparator.comparing(LinhaTempoDTO::ano).reversed())
                .toList();

        return new DadosPesquisadorDTO(
                pesquisador,
                endereco,
                formacoesAcademicas,
                idiomas,
                atuacoesProfissionais,
                artigos,
                livros,
                capitulos,
                trabalhosEventos,
                projetoPesquisas,
                premiacoes,
                orientacoes,
                tags,
                linhaDoTempoOrdenada
        );
    }

}
