package com.app.src.controllers;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.models.Usuario;
import com.app.src.services.FormacaoAcademicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formacoes")
public class FormacaoAcademicaController {

    @Autowired
    private FormacaoAcademicaService formacaoAcademicaService;

    @GetMapping("/listarFormacoes")
    public ResponseEntity<List<FormacaoAcademicaDTO>> listarTodos() {
        return ResponseEntity.ok(formacaoAcademicaService.buscarTodos());
    }

    // Buscar formação por ID
    @GetMapping("/listarFormacao/{id}")
    public ResponseEntity<FormacaoAcademicaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(formacaoAcademicaService.buscarPorId(id));
    }

    // Criar nova formação
    @PostMapping("/salvarFormacao")
    public ResponseEntity<FormacaoAcademicaDTO> criar(@RequestBody FormacaoAcademicaDTO formacaoDTO, 
                                                    @AuthenticationPrincipal Usuario usuarioLogado) {
        
        String login = usuarioLogado.getLogin();
        return ResponseEntity.ok(formacaoAcademicaService.salvar(formacaoDTO, login));
    }

    // Atualizar formação
    @PutMapping("/alterarFormacao/{id}")
    public ResponseEntity<FormacaoAcademicaDTO> atualizar
    (@PathVariable Integer id, @RequestBody FormacaoAcademicaDTO graduacaoAtualizada) {
        return ResponseEntity.ok(formacaoAcademicaService.atualizar(id, graduacaoAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirFormacao/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(formacaoAcademicaService.excluir(id));
    }
    
}
