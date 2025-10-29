package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.CapituloDTO;
import com.app.src.models.Capitulo;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface CapituloMapper extends GenericMapper<Capitulo, CapituloDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CapituloDTO dto, @MappingTarget Capitulo entity);
}
