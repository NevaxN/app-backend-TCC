package com.app.src.services;

import com.app.src.auth.enums.RoleName;
import com.app.src.auth.models.Role;
import com.app.src.repositories.RoleRepository;
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

import com.app.src.repositories.TipoUsuarioRepository;
import com.app.src.repositories.UsuarioRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UsuarioService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private RoleRepository roleRepository;

    public Usuario findById (int id) {
        return usuarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public boolean isUserVerified(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(NoSuchElementException::new);
        return usuario.isEmailVerificado();
    }

    public Usuario findByLogin (String login) {
        return usuarioRepository.findByLogin(login).orElseThrow(NoSuchElementException::new);
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

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

        String emailUsuario = criarUsuarioDTO.login();
        String senhaUsuario = criarUsuarioDTO.password();

        // Validar se o login já existe
        if (usuarioRepository.findByLogin(emailUsuario).isPresent()) {
            throw new RuntimeException("Erro: Login já está em uso!");
        }

        // Validar campos obrigatórios
        if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
            throw new RuntimeException("Login é obrigatório.");
        }
        if (senhaUsuario == null || senhaUsuario.trim().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória.");
        }

        // Processar tipo de usuário
        String tipoUsuarioStr = criarUsuarioDTO.tipo_usuario();
        TipoUsuarioName tipoUsuarioEnum = "pesquisador".equalsIgnoreCase(tipoUsuarioStr)
                ? TipoUsuarioName.PESQUISADOR
                : TipoUsuarioName.EMPRESA;

        TipoUsuario tipoUsuarioEntity = tipoUsuarioRepository.findByName(tipoUsuarioEnum)
                .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado"));

        // Buscamos a role padrão de usuário
        Role role = roleRepository.findByName(RoleName.ROLE_USUARIO).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Criar usuário com senha criptografada
        Usuario novoUsuario = Usuario.builder()
                .login(criarUsuarioDTO.login())
                .password(securityConfiguration.passwordEncoder().encode(criarUsuarioDTO.password()))
                .tipoUsuario(tipoUsuarioEntity)
                .roles(roles)
                .emailVerificado(false)
                .build();

        usuarioRepository.save(novoUsuario);

    }

}