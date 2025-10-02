package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.ProjetoPesquisaDTO;
import com.app.src.models.ProjetoPesquisa;

@Mapper
public interface ProjetoPesquisaMapper extends GenericMapper<ProjetoPesquisa, ProjetoPesquisaDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProjetoPesquisaDTO dto, ProjetoPesquisa entity);
}
