package com.app.src.mappers;

import com.app.src.dto.TagDTO;
import com.app.src.models.Tag;

public class TagMapper {
    public static TagDTO toDTO(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setPesquisador(tag.getPesquisador());
        dto.setListaTags(tag.getListaTags());
        return dto;
    }

    public static Tag toEntity(TagDTO dto) {
        if (dto == null) {
            return null;
        }

        Tag tag = new Tag();
        tag.setPesquisador(dto.getPesquisador());
        tag.setListaTags(dto.getListaTags());
        return tag;
    }
}
