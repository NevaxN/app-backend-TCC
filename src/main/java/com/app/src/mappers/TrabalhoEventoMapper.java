package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.TrabalhoEventoDTO;
import com.app.src.models.TrabalhoEvento;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface TrabalhoEventoMapper extends GenericMapper<TrabalhoEvento, TrabalhoEventoDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TrabalhoEventoDTO dto, @MappingTarget TrabalhoEvento entity);
}
