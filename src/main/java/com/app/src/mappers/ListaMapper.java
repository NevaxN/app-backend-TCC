package com.app.src.mappers;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.src.dto.ListaDTO;
import com.app.src.dto.PerfilSalvoDTO;
import com.app.src.models.Lista;
import com.app.src.models.Usuario;
import com.app.src.repositories.PesquisadorRepository;

@Mapper(
    componentModel = "spring", 
    uses = { PesquisadorMapper.class, UsuarioMapper.class }
)
public abstract class ListaMapper implements GenericMapper<Lista, ListaDTO>{

    @Autowired
    protected PesquisadorRepository pesquisadorRepository;

    @Mapping(target = "perfisSalvos", source = "perfisSalvos", qualifiedByName = "mapUsuariosToPerfis")
    public abstract ListaDTO toDTO(Lista entity);

    @Named("mapUsuariosToPerfis")
    protected Set<PerfilSalvoDTO> mapUsuariosToPerfis(Set<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            return Collections.emptySet();
        }

        // Para cada Usuario na lista, busque o Pesquisador e crie o DTO
        return usuarios.stream()
            .map(usuario -> {
                // Busca o pesquisador associado a este usuário
                return pesquisadorRepository.findByUsuarioId(usuario.getId())
                    .map(pesquisador -> {
                        // Se encontrou, cria o DTO com os nomes
                        PerfilSalvoDTO dto = new PerfilSalvoDTO();
                        dto.setId(pesquisador.getId()); // ID do Pesquisador
                        dto.setNome(pesquisador.getNomePesquisador());
                        dto.setSobrenome(pesquisador.getSobrenome());
                        return dto;
                    })
                    .orElse(null); // Retorna null se não achar (filtraremos depois)
            })
            .filter(dto -> dto != null) // Remove os nulos
            .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(ListaDTO dto, @MappingTarget Lista entity);
}
