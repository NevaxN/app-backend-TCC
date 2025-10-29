package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.OrientacaoDTO;
import com.app.src.models.Orientacao;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface OrientacaoMapper extends GenericMapper<Orientacao, OrientacaoDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrientacaoDTO dto, @MappingTarget Orientacao entity);
}
