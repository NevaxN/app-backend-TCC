package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.EmpresaDTO;
import com.app.src.models.Empresa;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface EmpresaMapper extends GenericMapper<Empresa, EmpresaDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmpresaDTO dto, @MappingTarget Empresa entity);
}
