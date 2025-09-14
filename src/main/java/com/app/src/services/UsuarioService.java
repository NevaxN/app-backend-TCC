package com.app.src.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.src.auth.config.SecurityConfiguration;
import com.app.src.auth.models.UsuarioDetailsImpl;
import com.app.src.auth.services.JwtTokenService;
import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.enums.TipoUsuarioName;
import com.app.src.models.TipoUsuario;
import com.app.src.models.Usuario;
import com.app.src.repositories.RoleRepository;
import com.app.src.repositories.TipoUsuarioRepository;
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
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public ResgatarJWTTokenDTO authenticateUser(LoginUsuarioDTO loginUsuarioDTO){
        // Validação básica
        if (loginUsuarioDTO.login() == null || loginUsuarioDTO.login().trim().isEmpty()) {
            throw new RuntimeException("Login é obrigatório");
        }
        if (loginUsuarioDTO.password() == null || loginUsuarioDTO.password().trim().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(
                loginUsuarioDTO.login(), 
                loginUsuarioDTO.password()
            );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        
        UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        return new ResgatarJWTTokenDTO(jwtTokenService.generateToken(usuarioDetails));
    }

    public void createUser(CriarUsuarioDTO criarUsuarioDTO){
        // Validar se o login já existe
        if (usuarioRepository.findByLogin(criarUsuarioDTO.login()).isPresent()) {
            throw new RuntimeException("Erro: Login já está em uso!");
        }

        // Validar campos obrigatórios
        if (criarUsuarioDTO.login() == null || criarUsuarioDTO.login().trim().isEmpty()) {
            throw new RuntimeException("Login é obrigatório");
        }
        if (criarUsuarioDTO.password() == null || criarUsuarioDTO.password().trim().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        // Processar tipo de usuário
        String tipoUsuarioStr = criarUsuarioDTO.tipo_usuario();
        TipoUsuarioName tipoUsuarioEnum = "pesquisador".equalsIgnoreCase(tipoUsuarioStr)
                ? TipoUsuarioName.PESQUISADOR
                : TipoUsuarioName.EMPRESA;

        TipoUsuario tipoUsuarioEntity = tipoUsuarioRepository.findByName(tipoUsuarioEnum)
                .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));

        // Criar usuário com senha criptografada
        Usuario novoUsuario = Usuario.builder()
                .login(criarUsuarioDTO.login())
                .password(securityConfiguration.passwordEncoder().encode(criarUsuarioDTO.password()))
                .tipoUsuario(tipoUsuarioEntity)
                .build();

        usuarioRepository.save(novoUsuario);
    }
}