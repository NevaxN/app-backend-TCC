package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.models.AtuacaoProfissional;

@Mapper
public interface AtuacaoProfissionalMapper extends GenericMapper<AtuacaoProfissional, AtuacaoProfissionalDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AtuacaoProfissionalDTO dto, AtuacaoProfissional entity);
}
