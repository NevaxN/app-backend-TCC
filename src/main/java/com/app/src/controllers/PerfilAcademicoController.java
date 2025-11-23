package com.app.src.controllers;

import com.app.src.dto.perfilAcademico.AlteracaoPerfilAcademicoDTO;
import com.app.src.services.PerfilAcademicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfilAcademico")
public class PerfilAcademicoController {

    private final PerfilAcademicoService perfilAcademicoService;

    public PerfilAcademicoController(PerfilAcademicoService perfilAcademicoService) {
        this.perfilAcademicoService = perfilAcademicoService;
    }

    @PutMapping("/{idPesquisador}")
    public ResponseEntity<String> atualizarPerfilAcademico (@RequestBody AlteracaoPerfilAcademicoDTO perfilAcademico, @PathVariable int idPesquisador) {
        return ResponseEntity.ok(perfilAcademicoService.atualizarPerfilAcademico(perfilAcademico, idPesquisador));
    }

}
