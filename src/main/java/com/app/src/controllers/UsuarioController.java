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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.dto.RespostaUsuarioDTO;
import com.app.src.dto.EsqueciSenhaDTO;
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

    // NOVO ENDPOINT: Obter dados completos do usuário logado
    @GetMapping("/dadosUsuario")
    public ResponseEntity<?> getDadosUsuario(@AuthenticationPrincipal Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            // Buscar dados completos do usuário
            Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("login", usuario.getLogin());
            response.put("emailVerificado", usuario.isEmailVerificado());
            response.put("tipoUsuario", usuario.getTipoUsuario().getName());

            // Se for pesquisador, buscar dados do pesquisador
            if (usuario.getTipoUsuario().getName().name().equals("PESQUISADOR")) {
                try {
                    var pesquisador = usuarioService.buscarPesquisadorPorUsuarioId(usuario.getId());
                    response.put("pesquisador", pesquisador);
                } catch (Exception e) {
                    // Pesquisador ainda não criado
                    response.put("pesquisador", null);
                }
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao buscar dados do usuário"));
        }
    }

    // NOVO ENDPOINT: Esqueci Senha
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody EsqueciSenhaDTO esqueciSenhaDTO) {
        try {
            String resultado = usuarioService.processarEsqueciSenha(esqueciSenhaDTO.email());
            return ResponseEntity.ok(Map.of("message", resultado));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", "Falha no processo", "message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // NOVO ENDPOINT: Verificar Token de Recuperação
    @GetMapping("/verificar-token-recuperacao")
    public ResponseEntity<?> verificarTokenRecuperacao(@RequestParam("token") String token) {
        try {
            String email = (String) redisTemplate.opsForValue().get("recuperacaoSenha:" + token);

            if (email == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Token inválido ou expirado"));
            }

            return ResponseEntity.ok(Map.of("valid", true, "email", email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao verificar token"));
        }
    }

    // NOVO ENDPOINT: Redefinir Senha
    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RedefinirSenhaRequest request) {
        try {
            String resultado = usuarioService.redefinirSenha(request.getToken(), request.getNovaSenha());
            return ResponseEntity.ok(Map.of("message", resultado));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", "Falha na redefinição", "message", e.getMessage()),
                    HttpStatus.BAD_REQUEST
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

// CLASSE AUXILIAR PARA O REQUEST DE REDEFINIR SENHA
class RedefinirSenhaRequest {
    private String token;
    private String novaSenha;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
}