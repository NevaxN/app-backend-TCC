package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.EventoDTO;
import com.app.src.models.Evento;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface EventoMapper extends GenericMapper<Evento, EventoDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EventoDTO dto, @MappingTarget Evento evento);
}
