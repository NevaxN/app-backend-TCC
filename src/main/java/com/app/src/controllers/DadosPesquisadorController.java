package com.app.src.controllers;

import com.app.src.dto.DadosPesquisadorDTO;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.services.DadosPesquisadorService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dadosPesquisador")
public class DadosPesquisadorController {

    @Autowired
    PesquisadorRepository pesquisadorRepository;

    private final DadosPesquisadorService dadosPesquisadorService;

    public DadosPesquisadorController(DadosPesquisadorService dadosPesquisadorService) {
        this.dadosPesquisadorService = dadosPesquisadorService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DadosPesquisadorDTO> buscarDadosPesquisadorPorId(@PathVariable Integer id) {
        Optional<Pesquisador> pesquisador = pesquisadorRepository.findByUsuarioId(id);

        return ResponseEntity.ok(dadosPesquisadorService.buscarDadosPesquisadorPorId(pesquisador.get().getId()));
    }

}
