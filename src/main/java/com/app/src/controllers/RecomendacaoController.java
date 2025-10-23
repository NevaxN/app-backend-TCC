package com.app.src.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.services.RecomendacaoService;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {
    
    @Autowired
    RecomendacaoService recomendacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PesquisadorDTO>> listarTodas(@PathVariable Integer id){
        return ResponseEntity.ok(recomendacaoService.getRecomendacao(id));
    }
}
