package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.models.Pesquisador;

@Mapper( componentModel = "spring", uses = {UsuarioMapper.class})
public interface PesquisadorMapper extends GenericMapper<Pesquisador, PesquisadorDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PesquisadorDTO dto, @MappingTarget Pesquisador entity);
}
