package com.app.src.controllers;

import com.app.src.dto.PesquisaDTO;
import com.app.src.services.PesquisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pesquisa")
public class PesquisaController {

    @Autowired
    private PesquisaService pesquisaService;

    @PostMapping("/buscar")
    public ResponseEntity<List<PesquisaDTO>> pesquisar(@RequestBody PesquisaRequest request) {
        return ResponseEntity.ok(pesquisaService.pesquisar(request.getTermo(), request.getTipo()));
    }
}

class PesquisaRequest {
    private String termo;
    private String tipo;
    
    public String getTermo() { return termo; }
    public void setTermo(String termo) { this.termo = termo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}