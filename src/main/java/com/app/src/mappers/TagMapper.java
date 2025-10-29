package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.TagDTO;
import com.app.src.models.Tag;

@Mapper(
    componentModel = "spring", 
    uses = { UsuarioMapper.class }
)
public interface TagMapper extends GenericMapper<Tag, TagDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TagDTO dto, @MappingTarget Tag entity);
}
