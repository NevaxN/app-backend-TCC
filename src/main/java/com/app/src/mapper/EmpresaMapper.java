package com.app.src.mapper;

import com.app.src.dto.EmpresaDTO;
import com.app.src.model.Empresa;

public class EmpresaMapper {
    public static EmpresaDTO toDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setPesquisador(empresa.getPesquisador());
        dto.setNome(empresa.getNome());
        return dto;
    }

    public static Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }

        Empresa empresa = new Empresa();
        empresa.setPesquisador(dto.getPesquisador());
        empresa.setNome(dto.getNome());
        return empresa;
    }
}
