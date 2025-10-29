package com.app.src.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.src.auth.enums.RoleName;
import com.app.src.auth.models.Role;
import com.app.src.dto.UsuarioDTO;
import com.app.src.enums.TipoUsuarioName;
import com.app.src.models.TipoUsuario;
import com.app.src.models.Usuario;
import com.app.src.repositories.RoleRepository;
import com.app.src.repositories.TipoUsuarioRepository;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper implements GenericMapper<Usuario, UsuarioDTO>{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    @Override
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToRoleNames")
    @Mapping(target = "tipoUsuario", source = "tipoUsuario", qualifiedByName = "tipoToString")
    public abstract UsuarioDTO toDTO(Usuario entity);

    @Named("rolesToRoleNames")
    protected Set<String> rolesToRoleNames(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());
    }

    @Named("tipoToString")
    protected String tipoToString(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.getName() == null) return null;
        return tipoUsuario.getName().name();
    }

    @Override
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNamesToRoles")
    @Mapping(target = "tipoUsuario", source = "tipoUsuario", qualifiedByName = "stringToTipo")
    @Mapping(target = "password", ignore = true)
    public abstract Usuario toEntity(UsuarioDTO dto);

    @Named("roleNamesToRoles")
    protected Set<Role> roleNamesToRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                     .map(name -> roleRepository.findByName(RoleName.valueOf(name))
                             .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                     .collect(Collectors.toSet());
    }

    @Named("stringToTipo")
    protected TipoUsuario stringToTipo(String tipoUsuarioName) {
        if (tipoUsuarioName == null) return null;
        return tipoUsuarioRepository.findByName(TipoUsuarioName.valueOf(tipoUsuarioName))
                 .orElseThrow(() -> new RuntimeException("TipoUsuario not found: " + tipoUsuarioName));
    }
}
