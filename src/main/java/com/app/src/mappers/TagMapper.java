package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.TagDTO;
import com.app.src.models.Tag;

@Mapper
public interface TagMapper extends GenericMapper<Tag, TagDTO>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TagDTO dto, Tag entity);
}
