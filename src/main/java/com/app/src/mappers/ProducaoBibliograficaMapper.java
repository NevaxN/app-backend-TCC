package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.ProducaoBibliograficaDTO;
import com.app.src.models.ProducaoBibliografica;

@Mapper
public interface ProducaoBibliograficaMapper extends GenericMapper<ProducaoBibliografica, ProducaoBibliograficaDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProducaoBibliograficaDTO dto, @MappingTarget ProducaoBibliografica entity);
}
