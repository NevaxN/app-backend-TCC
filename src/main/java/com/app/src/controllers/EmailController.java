package com.app.src.controllers;

import com.app.src.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/enviarVerificacao")
    public ResponseEntity<?> enviarVerificacaoDeEmail(@RequestParam("email") String email) {
        try {
            String resultado = emailService.enviarVerificacaoDeEmail(email);
            return ResponseEntity.ok(Map.of("message", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Novo endpoint para reenviar código de verificação
    @PostMapping("/reenviarVerificacao")
    public ResponseEntity<?> reenviarVerificacaoDeEmail(@RequestParam("email") String email) {
        try {
            String resultado = emailService.reenviarVerificacaoDeEmail(email);
            return ResponseEntity.ok(Map.of("message", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Novo endpoint para alterar email e reenviar verificação
    @PostMapping("/alterarEmail")
    public ResponseEntity<?> alterarEmailEReenviarVerificacao(
            @RequestParam("emailAntigo") String emailAntigo,
            @RequestParam("emailNovo") String emailNovo) {
        try {
            String resultado = emailService.alterarEmailEReenviarVerificacao(emailAntigo, emailNovo);
            return ResponseEntity.ok(Map.of("message", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}