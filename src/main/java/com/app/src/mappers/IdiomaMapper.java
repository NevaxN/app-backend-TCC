package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.IdiomaDTO;
import com.app.src.models.Idioma;

@Mapper
public interface IdiomaMapper extends GenericMapper<Idioma, IdiomaDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(IdiomaDTO dto, Idioma entity);
}
