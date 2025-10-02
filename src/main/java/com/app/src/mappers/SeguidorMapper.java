package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.SeguidorDTO;
import com.app.src.models.Seguidor;

@Mapper
public interface SeguidorMapper extends GenericMapper<Seguidor, SeguidorDTO>{
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SeguidorDTO dto, Seguidor entity);
}
