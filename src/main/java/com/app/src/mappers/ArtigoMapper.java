package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.ArtigoDTO;
import com.app.src.models.Artigo;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface ArtigoMapper extends GenericMapper<Artigo, ArtigoDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ArtigoDTO dto, @MappingTarget Artigo entity);
}
