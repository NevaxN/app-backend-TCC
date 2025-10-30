package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.models.ProjetoPesquisa;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface ProjetoPesquisaMapper extends GenericMapper<ProjetoPesquisa, ProjetoPesquisaDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProjetoPesquisaDTO dto, @MappingTarget ProjetoPesquisa entity);
}
