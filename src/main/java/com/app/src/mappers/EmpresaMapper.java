package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.EmpresaDTO;
import com.app.src.models.Empresa;

@Mapper
public interface EmpresaMapper extends GenericMapper<Empresa, EmpresaDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmpresaDTO dto, Empresa entity);
}
