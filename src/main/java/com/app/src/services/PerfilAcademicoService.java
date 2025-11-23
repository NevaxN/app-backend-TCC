package com.app.src.services;

import com.app.src.dto.*;
import com.app.src.mappers.AtuacaoProfissionalMapper;
import com.app.src.mappers.FormacaoAcademicaMapper;
import com.app.src.mappers.PesquisadorMapper;
import com.app.src.models.AtuacaoProfissional;
import com.app.src.models.FormacaoAcademica;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.AtuacaoProfissionalRepository;
import com.app.src.repositories.FormacaoAcademicaRepository;
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

    public PerfilAcademicoService(PesquisadorService pesquisadorService, PesquisadorMapper pesquisadorMapper, FormacaoAcademicaRepository formacaoAcademicaRepository, FormacaoAcademicaMapper formacaoAcademicaMapper, AtuacaoProfissionalRepository atuacaoProfissionalRepository, AtuacaoProfissionalMapper atuacaoProfissionalMapper) {
        this.pesquisadorService = pesquisadorService;
        this.pesquisadorMapper = pesquisadorMapper;
        this.formacaoAcademicaRepository = formacaoAcademicaRepository;
        this.formacaoAcademicaMapper = formacaoAcademicaMapper;
        this.atuacaoProfissionalRepository = atuacaoProfissionalRepository;
        this.atuacaoProfissionalMapper = atuacaoProfissionalMapper;
    }


    public String atualizarPerfilAcademico (AlteracaoPerfilAcademicoDTO perfilAcademico, int idPesquisador) {

        Pesquisador pesquisador = pesquisadorMapper.toEntity(pesquisadorService.buscarPorId(idPesquisador));

        try {
            atualizarFormacoesAcademicas(perfilAcademico.formacoesAcademicas(), pesquisador);
            atualizarAtuacoesProfissionais(perfilAcademico.atuacoesProfissionais(), pesquisador);

            return "Ok";
        } catch (Exception e) {
            return "Deu ruim";
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

}
