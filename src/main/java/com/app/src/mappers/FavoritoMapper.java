package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.FavoritoDTO;
import com.app.src.models.Favorito;

@Mapper
public interface FavoritoMapper extends GenericMapper<Favorito, FavoritoDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FavoritoDTO dto, @MappingTarget Favorito entity);
}
