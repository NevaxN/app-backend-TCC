package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.ListaDTO;
import com.app.src.models.Lista;

@Mapper(
    componentModel = "spring", 
    uses = { PesquisadorMapper.class, UsuarioMapper.class }
)
public interface ListaMapper extends GenericMapper<Lista, ListaDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ListaDTO dto, @MappingTarget Lista entity);
}
