package com.app.src.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.dto.RespostaUsuarioDTO;
import com.app.src.models.Usuario;
import com.app.src.repositories.UsuarioRepository;
import com.app.src.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        try {
            ResgatarJWTTokenDTO token = usuarioService.authenticateUser(loginUsuarioDTO);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Map.of("error", "Falha na autenticação", "message", e.getMessage()),
                HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PostMapping("/salvarUsuario")
    public ResponseEntity<?> criarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        try {
            usuarioService.createUser(criarUsuarioDTO);
            LoginUsuarioDTO login = new LoginUsuarioDTO(criarUsuarioDTO.login(), criarUsuarioDTO.password());
            ResgatarJWTTokenDTO token = usuarioService.authenticateUser(login);
            Optional<Usuario> usuario = usuarioRepository.findByLogin(criarUsuarioDTO.login());
            RespostaUsuarioDTO resposta = new RespostaUsuarioDTO(token, usuario.get().getId());
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                Map.of("error", "Falha ao criar usuário", "message", e.getMessage()),
                HttpStatus.BAD_REQUEST
            );
        }
    }
}