package com.app.src.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.models.FormacaoAcademica;

@Mapper
public interface FormacaoAcademicaMapper extends GenericMapper<FormacaoAcademica, FormacaoAcademicaDTO> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FormacaoAcademicaDTO dto, @MappingTarget FormacaoAcademica entity);
}
