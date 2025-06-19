package com.app.src.mapper;

import com.app.src.dto.EnderecoDTO;
import com.app.src.model.Endereco;

public class EnderecoMapper {
    public static EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setPesquisador(endereco.getPesquisador());
        dto.setPais(endereco.getPais());
        dto.setCidade(endereco.getCidade());
        dto.setBairro(endereco.getBairro());
        dto.setTelefone(endereco.getTelefone());
        dto.setEmail(endereco.getEmail());
        return dto;
    }

    public static Endereco toEntity(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setPesquisador(dto.getPesquisador());
        endereco.setPais(dto.getPais());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setTelefone(dto.getTelefone());
        endereco.setEmail(dto.getEmail());
        return endereco;
    }
}
