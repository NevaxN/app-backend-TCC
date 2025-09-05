package com.app.src.mappers;

import com.app.src.dto.EmpresaDTO;
import com.app.src.models.Empresa;

public class EmpresaMapper {
    public static EmpresaDTO toDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }

        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNomeRegistro(empresa.getNomeRegistro());
        dto.setNomeComercial(empresa.getNomeComercial());
        dto.setCnpj(empresa.getCnpj());
        dto.setBairro(empresa.getBairro());
        dto.setCep(empresa.getCep());
        dto.setCidade(empresa.getCidade());
        dto.setEmail(empresa.getEmail());
        dto.setEstado(empresa.getEstado());
        dto.setFrase(empresa.getFrase());
        dto.setNumeroEndereco(dto.getNumeroEndereco());
        dto.setSetor(empresa.getSetor());
        dto.setSite(empresa.getSite());
        dto.setTelefone(empresa.getTelefone());
        dto.setTextoEmpresa(empresa.getTextoEmpresa());
        dto.setLogradouro(empresa.getLogradouro());
        return dto;
    }

    public static Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }

        Empresa empresa = new Empresa();
        empresa.setNomeRegistro(dto.getNomeRegistro());
        empresa.setNomeComercial(dto.getNomeComercial());
        empresa.setCnpj(dto.getCnpj());
        empresa.setBairro(dto.getBairro());
        empresa.setCep(dto.getCep());
        empresa.setCidade(dto.getCidade());
        empresa.setEmail(dto.getEmail());
        empresa.setEstado(dto.getEstado());
        empresa.setFrase(dto.getFrase());
        empresa.setNumeroEndereco(dto.getNumeroEndereco());
        empresa.setSetor(dto.getSetor());
        empresa.setSite(dto.getSite());
        empresa.setTelefone(dto.getTelefone());
        empresa.setTextoEmpresa(dto.getTextoEmpresa());
        empresa.setLogradouro(dto.getLogradouro());
        return empresa;
    }
}
