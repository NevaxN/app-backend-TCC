package com.app.src.controllers;

import com.app.src.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/enviarVerificacao")
    public ResponseEntity<String> enviarVerificacaoDeEmail (@RequestParam("email") String email) {
        return ResponseEntity.ok(emailService.enviarVerificacaoDeEmail(email));
    }


}
