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
import com.app.src.repositories.EmpresaRepository;
import com.app.src.repositories.PesquisadorRepository;

@Mapper(
    componentModel = "spring", 
    uses = { PesquisadorMapper.class, UsuarioMapper.class }
)
public abstract class ListaMapper implements GenericMapper<Lista, ListaDTO>{

    @Autowired
    protected PesquisadorRepository pesquisadorRepository;

    @Autowired
    protected EmpresaRepository empresaRepository;

    @Mapping(target = "perfisSalvos", source = "perfisSalvos", qualifiedByName = "mapUsuariosToPerfis")
    public abstract ListaDTO toDTO(Lista entity);

    @Named("mapUsuariosToPerfis")
    protected Set<PerfilSalvoDTO> mapUsuariosToPerfis(Set<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            return Collections.emptySet();
        }

        return usuarios.stream()
            .map(usuario -> {
                String tipo = usuario.getTipoUsuario().getName().toString();
                
                // Objeto DTO que será preenchido
                PerfilSalvoDTO dto = new PerfilSalvoDTO();
                dto.setIdUsuario(usuario.getId());
                dto.setTipoPerfil(tipo);
                
                if ("PESQUISADOR".equals(tipo)) {
                    pesquisadorRepository.findByUsuarioId(usuario.getId()).ifPresent(pesquisador -> {
                        dto.setIdEntidade(pesquisador.getId());
                        dto.setNomeCompleto(pesquisador.getNomePesquisador() + " " + (pesquisador.getSobrenome() != null ? pesquisador.getSobrenome() : ""));
                        dto.setArea("Pesquisador"); 
                    });
                } else if ("EMPRESA".equals(tipo)) {
                    empresaRepository.findByUsuarioId(usuario.getId()).ifPresent(empresa -> {
                        dto.setIdEntidade(empresa.getId()); // ID da Entidade para rotas
                        dto.setNomeCompleto(empresa.getNomeComercial());
                        dto.setArea(empresa.getSetor() != null ? empresa.getSetor() : "Empresa");
                    });
                } else {
                    return null; 
                }

                if (dto.getNomeCompleto() == null) {
                    return null;
                }
                
                return dto;

            })
            .filter(dto -> dto != null)
            .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(ListaDTO dto, @MappingTarget Lista entity);
}
