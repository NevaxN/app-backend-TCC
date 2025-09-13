package com.app.src.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.src.models.TipoUsuario;
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

        // 1. Validar se o login ja existe para evitar erros no banco de dados
        if (usuarioRepository.findByLogin(criarUsuarioDTO.login()).isPresent()) {
            throw new RuntimeException("Erro: Login já está em uso!");
        }

        // 2. Processar as MÚLTIPLAS roles que vem do DTO
        Set<Role> userRoles = new HashSet<>();

        // Itera sobre cada nome de role recebido no DTO
        for (String roleStr : criarUsuarioDTO.roles()) {
            // A lógica aqui pode variar, to assumindo que que "admin" vira "ROLE_ADM" e "comum" vira "ROLE_USUARIO"
            RoleName roleEnum = "admin".equalsIgnoreCase(roleStr)
                    ? RoleName.ROLE_ADM
                    : RoleName.ROLE_USUARIO;

            Role userRoleEntity = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new RuntimeException("Erro: Role" + roleEnum + "não encontrada no banco de dados."));

            userRoles.add(userRoleEntity);
        }

        // 3. Coverte a string 'tipo_usuario' do DTO para o Enum
        TipoUsuario tipoUsuarioEnum = TipoUsuario.valueOf(criarUsuarioDTO.tipo_usuario().toUpperCase());

        Usuario novoUsuario = Usuario.builder()
                .login(criarUsuarioDTO.login())
                .password(securityConfiguration.passwordEncoder()
                        .encode(criarUsuarioDTO.password()))
                .tipoUsuario(tipoUsuarioEnum)
                .roles(userRoles)
                .build();

        usuarioRepository.save(novoUsuario);
    }
}
