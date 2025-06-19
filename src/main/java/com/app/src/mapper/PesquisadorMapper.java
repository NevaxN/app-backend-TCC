package com.app.src.mapper;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.model.Pesquisador;

public class PesquisadorMapper {
    public static PesquisadorDTO toDTO(Pesquisador pesquisador) {
        if (pesquisador == null) {
            return null;
        }

        PesquisadorDTO dto = new PesquisadorDTO();
        dto.setId(pesquisador.getId());
        dto.setUsuario(pesquisador.getUsuario());
        dto.setNomePesquisador(pesquisador.getNomePesquisador());
        dto.setSobrenome(pesquisador.getSobrenome());
        dto.setDataNascimento(pesquisador.getDataNascimento());
        dto.setNomeCitacoesBibliograficas(pesquisador.getNomeCitacoesBibliograficas());
        dto.setDataAtualizacao(pesquisador.getDataAtualizacao());
        dto.setHoraAtualizacao(pesquisador.getHoraAtualizacao());
        dto.setNacionalidade(pesquisador.getNacionalidade());
        dto.setPaisNascimento(pesquisador.getPaisNascimento());
        dto.setLattesId(pesquisador.getLattesId());
        dto.setImagemPerfil(pesquisador.getImagemPerfil());
        return dto;
    }

    public static Pesquisador toEntity(PesquisadorDTO dto) {
        if (dto == null) {
            return null;
        }

        Pesquisador pesquisador = new Pesquisador();
        pesquisador.setUsuario(dto.getUsuario());
        pesquisador.setNomePesquisador(dto.getNomePesquisador());
        pesquisador.setSobrenome(dto.getSobrenome());
        pesquisador.setDataNascimento(dto.getDataNascimento());
        pesquisador.setNomeCitacoesBibliograficas(dto.getNomeCitacoesBibliograficas());
        pesquisador.setDataAtualizacao(dto.getDataAtualizacao());
        pesquisador.setHoraAtualizacao(dto.getHoraAtualizacao());
        pesquisador.setNacionalidade(dto.getNacionalidade());
        pesquisador.setPaisNascimento(dto.getPaisNascimento());
        pesquisador.setLattesId(dto.getLattesId());
        pesquisador.setImagemPerfil(dto.getImagemPerfil());
        return pesquisador;
    }
}
