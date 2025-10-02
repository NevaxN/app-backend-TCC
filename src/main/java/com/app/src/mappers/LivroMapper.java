package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.LivroDTO;
import com.app.src.models.Livro;

@Mapper
public interface LivroMapper extends GenericMapper<Livro, LivroDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LivroDTO dto, @MappingTarget Livro entity);
}
