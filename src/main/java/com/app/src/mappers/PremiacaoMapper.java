package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.PremiacaoDTO;
import com.app.src.models.Premiacao;

@Mapper
public interface PremiacaoMapper extends GenericMapper<Premiacao, PremiacaoDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PremiacaoDTO dto, Premiacao entity);
}
