package com.app.src.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.app.src.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.dto.RespostaUsuarioDTO;
import com.app.src.repositories.UsuarioRepository;
import com.app.src.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        try {
            ResgatarJWTTokenDTO token = usuarioService.authenticateUser(loginUsuarioDTO);
            boolean emailVerificado = usuarioService.isUserVerified(loginUsuarioDTO.login());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token.token());
            response.put("emailVerificado", emailVerificado);
            return ResponseEntity.ok(response);

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

    @PostMapping("/verificarEmail")
    public ResponseEntity<?> verificarEmail(@RequestParam("codigo") String codigo) {

        String emailUsuario = (String) redisTemplate.opsForValue().getAndDelete("verificacaoEmail:" + codigo);

        if (emailUsuario == null) {
            return ResponseEntity.badRequest().body("Código inválido ou expirado.");
        }

        Usuario usuario = usuarioService.findByLogin(emailUsuario);

        if (usuario.isEmailVerificado()) {
            return ResponseEntity.badRequest().body("ERRO: Conta já verificada!");
        } else {
            usuario.setEmailVerificado(true);
            usuarioService.updateUsuario(usuario);
            return ResponseEntity.ok(
                    Map.of("tipo", usuario.getTipoUsuario().getName())
            );
        }

    }

    @GetMapping("/listarUsuario/{login:.+}")
    public ResponseEntity<?> buscarUsuarioPorLogin(@PathVariable String login){
        if(login == null){
            throw new NoSuchElementException("Usuario não encontrado com login: " + login);
        }
        return ResponseEntity.ok(usuarioRepository.findByLogin(login));
    }
}