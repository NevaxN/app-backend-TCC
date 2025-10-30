package com.app.src.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.src.dto.SeguidorDTO;
import com.app.src.models.Seguidor;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface SeguidorMapper extends GenericMapper<Seguidor, SeguidorDTO>{
    
    @Mapping(source = "pesquisador.id", target = "pesquisadorId")
    @Override
    SeguidorDTO toDTO(Seguidor entity);

    // Mapeia DTO -> E (Ignora o ID, pois o serviço cuida disso)
    @Mapping(target = "pesquisador", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Override
    Seguidor toEntity(SeguidorDTO dto);
}
