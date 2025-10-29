package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.EnderecoDTO;
import com.app.src.models.Endereco;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface EnderecoMapper extends GenericMapper<Endereco, EnderecoDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EnderecoDTO dto, @MappingTarget Endereco entity);
}
