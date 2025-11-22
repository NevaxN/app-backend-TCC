package com.app.src.controllers;

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

import com.app.src.dto.AlterarLoginDTO;
import com.app.src.dto.CriarUsuarioDTO;
import com.app.src.dto.LoginUsuarioDTO;
import com.app.src.dto.ResgatarJWTTokenDTO;
import com.app.src.dto.RespostaUsuarioDTO;
import com.app.src.repositories.EmpresaRepository;
import com.app.src.repositories.PesquisadorRepository;
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

    @Autowired
    private PesquisadorRepository pesquisadorRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

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

    @PutMapping("/alterarLogin")
    public ResponseEntity<?> alterarLogin(@RequestBody AlterarLoginDTO dto, 
                                        @AuthenticationPrincipal Usuario usuarioLogado){

        String loginAtual = usuarioLogado.getLogin();
        Optional<Usuario> usuario = usuarioRepository.findByLogin(loginAtual);

        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        usuarioRepository.updateLogin(usuario.get().getId(), dto.novoLogin());

        return ResponseEntity.ok("Login alterado com sucesso.");
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

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer usuarioId){ 

        pesquisadorRepository.findByUsuarioId(usuarioId).ifPresent(pesquisador -> {
            pesquisadorRepository.delete(pesquisador);
        });

        empresaRepository.findByUsuarioId(usuarioId).ifPresent(empresa -> {
            empresaRepository.delete(empresa);
        });
        
        usuarioRepository.deleteById(usuarioId);
        
        return ResponseEntity.ok("Usuário e perfil excluídos com sucesso");
    }
}