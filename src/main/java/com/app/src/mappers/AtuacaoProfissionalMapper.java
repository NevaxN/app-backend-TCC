package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.models.AtuacaoProfissional;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface AtuacaoProfissionalMapper extends GenericMapper<AtuacaoProfissional, AtuacaoProfissionalDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AtuacaoProfissionalDTO dto, @MappingTarget AtuacaoProfissional entity);
}
