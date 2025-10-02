package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.EnderecoDTO;
import com.app.src.models.Endereco;

@Mapper
public interface EnderecoMapper extends GenericMapper<Endereco, EnderecoDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EnderecoDTO dto, Endereco entity);
}
