package com.app.src.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.src.auth.config.SecurityConfiguration;
import com.app.src.auth.enums.Role;
import com.app.src.auth.models.RoleName;
import com.app.src.auth.models.UsuarioDetailsImpl;
import com.app.src.auth.services.JwtTokenService;
import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.models.Usuario;
import com.app.src.repositories.RoleRepository;
import com.app.src.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public ResgatarJWTTokenDTO authenticateUser(LoginUsuarioDTO loginUsuarioDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(loginUsuarioDTO.login(), 
            loginUsuarioDTO.password());

        Authentication authentication = authenticationManager
            .authenticate(usernamePasswordAuthenticationToken);
        
        UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        return new ResgatarJWTTokenDTO(jwtTokenService.generateToken(usuarioDetails));
    }

    public void createUser(CriarUsuarioDTO criarUsuarioDTO){

        RoleName roleEnum = "admin".equalsIgnoreCase(criarUsuarioDTO.role())
            ? RoleName.ROLE_ADM
            : RoleName.ROLE_USUARIO;

        Role userRoleEntity = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Erro: Role" + roleEnum + "não encontrada no banco de dados."));

        Usuario novoUsuario = Usuario.builder()
                    .login(criarUsuarioDTO.login())
                    .password(securityConfiguration.passwordEncoder()
                    .encode(criarUsuarioDTO.password()))
                    .roles(List.of(userRoleEntity))
                    .build();

        usuarioRepository.save(novoUsuario);
    }
}
