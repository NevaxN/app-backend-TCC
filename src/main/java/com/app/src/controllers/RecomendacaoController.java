package com.app.src.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.models.Usuario;
import com.app.src.services.RecomendacaoService;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {
    
    @Autowired
    RecomendacaoService recomendacaoService;

    @GetMapping
    public ResponseEntity<List<PesquisadorDTO>> getRecomendacoes(@AuthenticationPrincipal Usuario usuarioLogado){
        if (usuarioLogado == null){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(recomendacaoService.getRecomendacao(usuarioLogado.getId()));
    }
}
