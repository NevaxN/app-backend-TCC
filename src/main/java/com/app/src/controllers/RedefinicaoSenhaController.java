package com.app.src.controllers;

import com.app.src.dto.AlterarSenhaDTO;
import com.app.src.services.EmailService;
import com.app.src.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redefinicao")
public class RedefinicaoSenhaController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/enviar")
    public ResponseEntity<?> enviar (@RequestParam("email") String email) {
        return ResponseEntity.ok(emailService.enviarRedefinicaoDeSenha(email));
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validar (@RequestParam("codigo") String codigo) {
        return ResponseEntity.ok(redisTemplate.hasKey("redefinicaoSenha:" + codigo));
    }

    @PostMapping("/alterar")
    public ResponseEntity<String> alterarSenha(@RequestBody AlterarSenhaDTO alterarSenhaDTO) {
        usuarioService.alterarSenha(alterarSenhaDTO);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }

}
