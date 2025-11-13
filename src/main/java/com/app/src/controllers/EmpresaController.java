package com.app.src.controllers;

import com.app.src.dto.EmpresaDTO;
import com.app.src.models.Usuario;
import com.app.src.services.EmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
        
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/listarEmpresas")
    public ResponseEntity<List<EmpresaDTO>> listarTodos() {
        return ResponseEntity.ok(empresaService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEmpresa/{id}")
    public ResponseEntity<EmpresaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarEmpresa")
    public ResponseEntity<EmpresaDTO> salvar(@RequestBody EmpresaDTO empresaDTO,
                                            @AuthenticationPrincipal Usuario usuarioLogado) {

        if (usuarioLogado == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(empresaService.salvar(empresaDTO, usuarioLogado));
    }

    // Atualizar endereço
    @PutMapping("/alterarEmpresa/{id}")
    public ResponseEntity<EmpresaDTO> atualizar(@PathVariable Integer id, @RequestBody EmpresaDTO empresaAtualizada) {
        return ResponseEntity.ok(empresaService.atualizar(id, empresaAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirEmpresa/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.excluir(id));
    }
}
