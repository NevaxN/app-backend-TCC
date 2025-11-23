package com.app.src.services;

import com.app.src.dto.*;
import com.app.src.dto.perfilAcademico.*;
import com.app.src.mappers.*;
import com.app.src.models.*;
import com.app.src.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilAcademicoService {

    private final PesquisadorService pesquisadorService;
    private final PesquisadorMapper pesquisadorMapper;

    private final FormacaoAcademicaRepository formacaoAcademicaRepository;
    private final FormacaoAcademicaMapper formacaoAcademicaMapper;

    private final AtuacaoProfissionalRepository atuacaoProfissionalRepository;
    private final AtuacaoProfissionalMapper atuacaoProfissionalMapper;

    private final ArtigoRepository artigoRepository;
    private final ArtigoMapper artigoMapper;

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    private final CapituloRepository capituloRepository;
    private final CapituloMapper capituloMapper;

    private final OrientacaoRepository orientacaoRepository;
    private final OrientacaoMapper orientacaoMapper;

    private final PremiacaoRepository premiacaoRepository;
    private final PremiacaoMapper premiacaoMapper;

    private final ProjetoPesquisaRepository projetoPesquisaRepository;
    private final ProjetoPesquisaMapper projetoPesquisaMapper;

    private final TrabalhoEventoRepository trabalhoEventoRepository;
    private final TrabalhoEventoMapper trabalhoEventoMapper;

    public PerfilAcademicoService(PesquisadorService pesquisadorService, PesquisadorMapper pesquisadorMapper, FormacaoAcademicaRepository formacaoAcademicaRepository, FormacaoAcademicaMapper formacaoAcademicaMapper, AtuacaoProfissionalRepository atuacaoProfissionalRepository, AtuacaoProfissionalMapper atuacaoProfissionalMapper, ArtigoRepository artigoRepository, ArtigoMapper artigoMapper, LivroRepository livroRepository, LivroMapper livroMapper, CapituloRepository capituloRepository, CapituloMapper capituloMapper, OrientacaoRepository orientacaoRepository, OrientacaoMapper orientacaoMapper, PremiacaoRepository premiacaoRepository, PremiacaoMapper premiacaoMapper, ProjetoPesquisaRepository projetoPesquisaRepository, ProjetoPesquisaMapper projetoPesquisaMapper, TrabalhoEventoRepository trabalhoEventoRepository, TrabalhoEventoMapper trabalhoEventoMapper) {
        this.pesquisadorService = pesquisadorService;
        this.pesquisadorMapper = pesquisadorMapper;
        this.formacaoAcademicaRepository = formacaoAcademicaRepository;
        this.formacaoAcademicaMapper = formacaoAcademicaMapper;
        this.atuacaoProfissionalRepository = atuacaoProfissionalRepository;
        this.atuacaoProfissionalMapper = atuacaoProfissionalMapper;
        this.artigoRepository = artigoRepository;
        this.artigoMapper = artigoMapper;
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
        this.capituloRepository = capituloRepository;
        this.capituloMapper = capituloMapper;
        this.orientacaoRepository = orientacaoRepository;
        this.orientacaoMapper = orientacaoMapper;
        this.premiacaoRepository = premiacaoRepository;
        this.premiacaoMapper = premiacaoMapper;
        this.projetoPesquisaRepository = projetoPesquisaRepository;
        this.projetoPesquisaMapper = projetoPesquisaMapper;
        this.trabalhoEventoRepository = trabalhoEventoRepository;
        this.trabalhoEventoMapper = trabalhoEventoMapper;
    }


    public String atualizarPerfilAcademico (AlteracaoPerfilAcademicoDTO perfilAcademico, int idPesquisador) {
        Pesquisador pesquisador = pesquisadorMapper.toEntity(pesquisadorService.buscarPorId(idPesquisador));

        try {
            atualizarFormacoesAcademicas(perfilAcademico.formacoesAcademicas(), pesquisador);
            atualizarAtuacoesProfissionais(perfilAcademico.atuacoesProfissionais(), pesquisador);
            atualizarArtigos(perfilAcademico.artigos(), pesquisador);
            atualizarLivros(perfilAcademico.livros(), pesquisador);
            atualizarCapitulos(perfilAcademico.capitulos(), pesquisador);
            atualizarOrientacoes(perfilAcademico.orientacoes(), pesquisador);
            atualizarPremiacoes(perfilAcademico.premiacoes(), pesquisador);
            atualizarProjetosPesquisa(perfilAcademico.projetosPesquisa(), pesquisador);
            atualizarTrabalhosEvento(perfilAcademico.trabalhosEventos(), pesquisador);
            return "Ok";
        } catch (Exception e) {
            return "Deu ruim";
        }
    }

    private void atualizarLivros (ListaPerfilAcademicoDTO< LivroSemPesquisadorDTO, LivroDTO> livros, Pesquisador pesquisador) {
        if (!livros.adicionados().isEmpty()) {
            List<Livro> livrosParaSalvar = new ArrayList<>();
            for (LivroSemPesquisadorDTO livro : livros.adicionados()) {
                Livro livroParaSalvar = getLivro(pesquisador, livro);
                livrosParaSalvar.add(livroParaSalvar);
            }
            livroRepository.saveAll(livrosParaSalvar);
        }

        if (!livros.editados().isEmpty()) {
            List<Livro> livrosParaAtualizar = new ArrayList<>();
            for (LivroDTO livro : livros.editados()) {
                livrosParaAtualizar.add(livroMapper.toEntity(livro));
            }
            livroRepository.saveAll(livrosParaAtualizar);
        }

        if (!livros.deletados().isEmpty()) {
            livroRepository.deleteAllByIdInBatch(livros.deletados());
        }
    }

    private void atualizarCapitulos (ListaPerfilAcademicoDTO< CapituloSemPesquisadorDTO, CapituloDTO> capitulos, Pesquisador pesquisador) {
        if (!capitulos.adicionados().isEmpty()) {
            List<Capitulo> capitulosParaSalvar = new ArrayList<>();
            for (CapituloSemPesquisadorDTO capitulo : capitulos.adicionados()) {
                Capitulo capituloParaSalvar = getCapitulo(pesquisador, capitulo);
                capitulosParaSalvar.add(capituloParaSalvar);
            }
            capituloRepository.saveAll(capitulosParaSalvar);
        }

        if (!capitulos.editados().isEmpty()) {
            List<Capitulo> capitulosParaAtualizar = new ArrayList<>();
            for (CapituloDTO capitulo : capitulos.editados()) {
                capitulosParaAtualizar.add(capituloMapper.toEntity(capitulo));
            }
            capituloRepository.saveAll(capitulosParaAtualizar);
        }

        if (!capitulos.deletados().isEmpty()) {
            capituloRepository.deleteAllByIdInBatch(capitulos.deletados());
        }
    }

    private void atualizarArtigos (ListaPerfilAcademicoDTO<ArtigoSemPesquisadorDTO, ArtigoDTO> artigos, Pesquisador pesquisador) {
        if (!artigos.adicionados().isEmpty()) {
            List<Artigo> artigosParaSalvar = new ArrayList<>();
            for (ArtigoSemPesquisadorDTO artigo : artigos.adicionados()) {
                Artigo artigoParaSalvar = getArtigo(pesquisador, artigo);
                artigosParaSalvar.add(artigoParaSalvar);
            }
            artigoRepository.saveAll(artigosParaSalvar);
        }

        if (!artigos.editados().isEmpty()) {
            List<Artigo> artigosParaAtualizar = new ArrayList<>();
            for (ArtigoDTO artigo : artigos.editados()) {
                artigosParaAtualizar.add(artigoMapper.toEntity(artigo));
            }
            artigoRepository.saveAll(artigosParaAtualizar);
        }

        if (!artigos.deletados().isEmpty()) {
            artigoRepository.deleteAllByIdInBatch(artigos.deletados());
        }
    }

    private void atualizarAtuacoesProfissionais (ListaPerfilAcademicoDTO<AtuacaoProfissionalSemPesquisadorDTO, AtuacaoProfissionalDTO> atuacoes, Pesquisador pesquisador) {
        if (!atuacoes.adicionados().isEmpty()) {
            List<AtuacaoProfissional> atuacoesParaSalvar = new ArrayList<>();
            for (AtuacaoProfissionalSemPesquisadorDTO atuacao : atuacoes.adicionados()) {
                AtuacaoProfissional atuacaoProfissional = getAtuacaoProfissional(pesquisador, atuacao);
                atuacoesParaSalvar.add(atuacaoProfissional);
            }
            atuacaoProfissionalRepository.saveAll(atuacoesParaSalvar);
        }

        if (!atuacoes.editados().isEmpty()) {
            List<AtuacaoProfissional> atuacoesParaAtualizar = new ArrayList<>();
            for (AtuacaoProfissionalDTO atuacao : atuacoes.editados()) {
                atuacoesParaAtualizar.add(atuacaoProfissionalMapper.toEntity(atuacao));
            }
            atuacaoProfissionalRepository.saveAll(atuacoesParaAtualizar);
        }

        if (!atuacoes.deletados().isEmpty()) {
            atuacaoProfissionalRepository.deleteAllByIdInBatch(atuacoes.deletados());
        }
    }

    private void atualizarFormacoesAcademicas (ListaPerfilAcademicoDTO<FormacaoAcademicaSemPesquisadorDTO, FormacaoAcademicaDTO> formacoes, Pesquisador pesquisador) {
        if (!formacoes.adicionados().isEmpty()) {
            List<FormacaoAcademica> formacoesParaSalvar = new ArrayList<>();
            for (FormacaoAcademicaSemPesquisadorDTO formacao : formacoes.adicionados()) {
                FormacaoAcademica formacaoAcademica = getFormacaoAcademica(pesquisador, formacao);
                formacoesParaSalvar.add(formacaoAcademica);
            }
            formacaoAcademicaRepository.saveAll(formacoesParaSalvar);
        }

        if (!formacoes.editados().isEmpty()) {
            List<FormacaoAcademica> formacoesParaAtualizar = new ArrayList<>();
            for (FormacaoAcademicaDTO formacao : formacoes.editados()) {
                formacoesParaAtualizar.add(formacaoAcademicaMapper.toEntity(formacao));
            }
            formacaoAcademicaRepository.saveAll(formacoesParaAtualizar);
        }

        if (!formacoes.deletados().isEmpty()) {
            formacaoAcademicaRepository.deleteAllByIdInBatch(formacoes.deletados());
        }
    }

    private void atualizarOrientacoes (ListaPerfilAcademicoDTO<OrientacoesSemPesquisadorDTO, OrientacaoDTO> orientacoes, Pesquisador pesquisador) {
        if (!orientacoes.adicionados().isEmpty()) {
            List<Orientacao> orientacoesParaSalvar = new ArrayList<>();
            for (OrientacoesSemPesquisadorDTO orientacao : orientacoes.adicionados()) {
                Orientacao orientacaoParaSalvar = getOrientacao(pesquisador, orientacao);
                orientacoesParaSalvar.add(orientacaoParaSalvar);
            }
            orientacaoRepository.saveAll(orientacoesParaSalvar);
        }

        if (!orientacoes.editados().isEmpty()) {
            List<Orientacao> orientacoesParaAtualizar = new ArrayList<>();
            for (OrientacaoDTO orientacao : orientacoes.editados()) {
                orientacoesParaAtualizar.add(orientacaoMapper.toEntity(orientacao));
            }
            orientacaoRepository.saveAll(orientacoesParaAtualizar);
        }

        if (!orientacoes.deletados().isEmpty()) {
            orientacaoRepository.deleteAllByIdInBatch(orientacoes.deletados());
        }
    }

    private void atualizarPremiacoes (ListaPerfilAcademicoDTO<PremiacaoSemPesquisadorDTO, PremiacaoDTO> premiacoes, Pesquisador pesquisador) {
        if (!premiacoes.adicionados().isEmpty()) {
            List<Premiacao> premiacoesParaSalvar = new ArrayList<>();
            for (PremiacaoSemPesquisadorDTO premiacao : premiacoes.adicionados()) {
                Premiacao premiacaoParaSalvar = getPremiacao(pesquisador, premiacao);
                premiacoesParaSalvar.add(premiacaoParaSalvar);
            }
            premiacaoRepository.saveAll(premiacoesParaSalvar);
        }

        if (!premiacoes.editados().isEmpty()) {
            List<Premiacao> premiacoesParaAtualizar = new ArrayList<>();
            for (PremiacaoDTO premiacao : premiacoes.editados()) {
                premiacoesParaAtualizar.add(premiacaoMapper.toEntity(premiacao));
            }
            premiacaoRepository.saveAll(premiacoesParaAtualizar);
        }

        if (!premiacoes.deletados().isEmpty()) {
            premiacaoRepository.deleteAllByIdInBatch(premiacoes.deletados());
        }
    }

    private void atualizarProjetosPesquisa (ListaPerfilAcademicoDTO<ProjetoPesquisaSemPesquisadorDTO, ProjetoPesquisaDTO> projetos, Pesquisador pesquisador) {
        if (!projetos.adicionados().isEmpty()) {
            List<ProjetoPesquisa> projetosParaSalvar = new ArrayList<>();
            for (ProjetoPesquisaSemPesquisadorDTO projeto : projetos.adicionados()) {
                ProjetoPesquisa projetoParaSalvar = getProjetoPesquisa(pesquisador, projeto);
                projetosParaSalvar.add(projetoParaSalvar);
            }
            projetoPesquisaRepository.saveAll(projetosParaSalvar);
        }

        if (!projetos.editados().isEmpty()) {
            List<ProjetoPesquisa> projetosParaAtualizar = new ArrayList<>();
            for (ProjetoPesquisaDTO projeto : projetos.editados()) {
                projetosParaAtualizar.add(projetoPesquisaMapper.toEntity(projeto));
            }
            projetoPesquisaRepository.saveAll(projetosParaAtualizar);
        }

        if (!projetos.deletados().isEmpty()) {
            projetoPesquisaRepository.deleteAllByIdInBatch(projetos.deletados());
        }
    }

    private void atualizarTrabalhosEvento (ListaPerfilAcademicoDTO<TrabalhoEventoSemPesquisadorDTO, TrabalhoEventoDTO> trabalhos, Pesquisador pesquisador) {
        if (!trabalhos.adicionados().isEmpty()) {
            List<TrabalhoEvento> trabalhosParaSalvar = new ArrayList<>();
            for (TrabalhoEventoSemPesquisadorDTO trabalho : trabalhos.adicionados()) {
                TrabalhoEvento trabalhoParaSalvar = getTrabalhoEvento(pesquisador, trabalho);
                trabalhosParaSalvar.add(trabalhoParaSalvar);
            }
            trabalhoEventoRepository.saveAll(trabalhosParaSalvar);
        }

        if (!trabalhos.editados().isEmpty()) {
            List<TrabalhoEvento> trabalhosParaAtualizar = new ArrayList<>();
            for (TrabalhoEventoDTO trabalho : trabalhos.editados()) {
                trabalhosParaAtualizar.add(trabalhoEventoMapper.toEntity(trabalho));
            }
            trabalhoEventoRepository.saveAll(trabalhosParaAtualizar);
        }

        if (!trabalhos.deletados().isEmpty()) {
            trabalhoEventoRepository.deleteAllByIdInBatch(trabalhos.deletados());
        }
    }

    private static FormacaoAcademica getFormacaoAcademica(Pesquisador pesquisador, FormacaoAcademicaSemPesquisadorDTO formacao) {
        FormacaoAcademica formacaoAcademica = new FormacaoAcademica();
        formacaoAcademica.setPesquisador(pesquisador);
        formacaoAcademica.setStatus(formacao.status());
        formacaoAcademica.setCurso(formacao.curso());
        formacaoAcademica.setAnoConclusao(formacao.anoConclusao());
        formacaoAcademica.setAnoInicio(formacao.anoInicio());
        formacaoAcademica.setTituloTrabalho(formacao.tituloTrabalho());
        formacaoAcademica.setInstituicao(formacao.instituicao());
        formacaoAcademica.setSequenciaFormacao(100);
        formacaoAcademica.setDestaque(formacao.destaque());
        formacaoAcademica.setOrientador(formacao.orientador());
        formacaoAcademica.setNivel(formacao.nivel());
        return formacaoAcademica;
    }

    private static AtuacaoProfissional getAtuacaoProfissional(Pesquisador pesquisador, AtuacaoProfissionalSemPesquisadorDTO atuacao) {
        AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();
        atuacaoProfissional.setPesquisador(pesquisador);
        atuacaoProfissional.setCargo(atuacao.cargo());
        atuacaoProfissional.setInstituicao(atuacao.instituicao());
        atuacaoProfissional.setDestaque(atuacao.destaque());
        atuacaoProfissional.setAnoInicio(atuacao.anoInicio());
        atuacaoProfissional.setAnoFim(atuacao.anoFim());
        atuacaoProfissional.setMesInicio(0);
        atuacaoProfissional.setMesFim(0);
        atuacaoProfissional.setSequenciaAtuacao(0);
        atuacaoProfissional.setSequenciaVinculo(0);
        return atuacaoProfissional;
    }

    private static Artigo getArtigo (Pesquisador pesquisador, ArtigoSemPesquisadorDTO artigoIncompleto) {
        Artigo artigo= new Artigo();
        artigo.setPesquisador(pesquisador);
        artigo.setDestaque(artigoIncompleto.destaque());
        artigo.setIdioma(artigoIncompleto.idioma());
        artigo.setDoi(artigoIncompleto.doi());
        artigo.setTitulo(artigoIncompleto.titulo());
        artigo.setAutores(artigoIncompleto.autores());
        artigo.setPeriodico(artigoIncompleto.periodico());
        return artigo;
    }

    private static Livro getLivro (Pesquisador pesquisador, LivroSemPesquisadorDTO livroIncompleto) {
        Livro livro = new Livro();
        livro.setPesquisador(pesquisador);
        livro.setDestaque(livroIncompleto.destaque());
        livro.setIdioma(livroIncompleto.idioma());
        livro.setTitulo(livroIncompleto.titulo());
        livro.setAutores(livroIncompleto.autores());
        livro.setNumeroPaginas(livroIncompleto.numeroPaginas());
        livro.setIsbn(livroIncompleto.isbn());
        livro.setEditora(livroIncompleto.editora());
        return livro;
    }

    private static Orientacao getOrientacao (Pesquisador pesquisador, OrientacoesSemPesquisadorDTO orientacaoIncompleta) {
        Orientacao orientacao = new Orientacao();
        orientacao.setPesquisador(pesquisador);
        orientacao.setTipo(orientacaoIncompleta.tipo());
        orientacao.setNomeOrientado(orientacaoIncompleta.nomeOrientado());
        orientacao.setNomeCurso(orientacaoIncompleta.nomeCurso());
        orientacao.setTituloTrabalho(orientacaoIncompleta.tituloTrabalho());
        orientacao.setInstituicao(orientacaoIncompleta.instituicao());
        orientacao.setAno(orientacaoIncompleta.ano());
        orientacao.setSequencia(orientacaoIncompleta.sequencia());
        orientacao.setDestaque(orientacaoIncompleta.destaque());
        return orientacao;
    }

    private static Premiacao getPremiacao (Pesquisador pesquisador, PremiacaoSemPesquisadorDTO premiacaoIncompleta) {
        Premiacao premiacao = new Premiacao();
        premiacao.setPesquisador(pesquisador);
        premiacao.setTitulo(premiacaoIncompleta.titulo());
        premiacao.setInstituicao(premiacaoIncompleta.instituicao());
        premiacao.setAno(premiacaoIncompleta.ano());
        premiacao.setDestaque(premiacaoIncompleta.destaque());
        return premiacao;
    }

    private static ProjetoPesquisa getProjetoPesquisa (Pesquisador pesquisador, ProjetoPesquisaSemPesquisadorDTO projetoIncompleto) {
        ProjetoPesquisa projeto = new ProjetoPesquisa();
        projeto.setPesquisador(pesquisador);
        projeto.setTitulo(projetoIncompleto.titulo());
        projeto.setDescricao(projetoIncompleto.descricao());
        projeto.setInstituicao(projetoIncompleto.instituicao());
        projeto.setAno(projetoIncompleto.ano());
        projeto.setFinanciador(projetoIncompleto.financiador());
        projeto.setDestaque(projetoIncompleto.destaque());
        projeto.setSequencia(0);
        return projeto;
    }

    private static TrabalhoEvento getTrabalhoEvento (Pesquisador pesquisador, TrabalhoEventoSemPesquisadorDTO trabalhoIncompleto) {
        TrabalhoEvento trabalho = new TrabalhoEvento();
        trabalho.setPesquisador(pesquisador);
        trabalho.setSequenciaProducao(trabalhoIncompleto.sequenciaProducao());
        trabalho.setAutores(trabalhoIncompleto.autores());
        trabalho.setAno(trabalhoIncompleto.ano());
        trabalho.setDestaque(trabalhoIncompleto.destaque());
        trabalho.setTitulo(trabalhoIncompleto.titulo());
        trabalho.setClassificacaoEvento(trabalhoIncompleto.classificacaoEvento());
        trabalho.setNomeEvento(trabalhoIncompleto.nomeEvento());
        trabalho.setCidadeEvento(trabalhoIncompleto.cidadeEvento());
        return trabalho;
    }

    private static Capitulo getCapitulo (Pesquisador pesquisador, CapituloSemPesquisadorDTO capituloIncompleto) {
        Capitulo capitulo = new Capitulo();
        capitulo.setPesquisador(pesquisador);
        capitulo.setSequenciaProducao(capituloIncompleto.sequenciaProducao());
        capitulo.setAutores(capituloIncompleto.autores());
        capitulo.setAno(capituloIncompleto.ano());
        capitulo.setDestaque(capituloIncompleto.destaque());
        capitulo.setTituloCapitulo(capituloIncompleto.tituloCapitulo());
        capitulo.setNomeLivro(capituloIncompleto.nomeLivro());
        capitulo.setEditora(capituloIncompleto.editora());
        capitulo.setIdioma(capituloIncompleto.idioma());
        capitulo.setDoi(capituloIncompleto.doi());
        capitulo.setPaginaInicial(capituloIncompleto.paginaInicial());
        capitulo.setPaginaFinal(capituloIncompleto.paginaFinal());
        return capitulo;
    }

}