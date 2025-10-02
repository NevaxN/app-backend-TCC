package com.app.src.controllers;

import com.app.src.dto.ListaDTO;
import com.app.src.services.ListaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    

    @GetMapping("/listarListas")
    public ResponseEntity<List<ListaDTO>> listarTodos() {
        return ResponseEntity.ok(listaService.buscarTodos());
    }

    // Buscar endereço por ID
    @GetMapping("/listarLista/{id}")
    public ResponseEntity<ListaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(listaService.buscarPorId(id));
    }

    // Criar novo endereço
    @PostMapping("/salvarLista")
    public ResponseEntity<ListaDTO> criar(@RequestBody ListaDTO listaDTO) {
        return ResponseEntity.ok(listaService.salvar(listaDTO));
    }

    @PostMapping("/salvarLista/{listaId}/perfil/{usuarioId}")
    public ResponseEntity<Void> adicionarPerfilNaLista(@PathVariable Integer listaId, @PathVariable Integer usuarioId){
        listaService.adicionarPerfilNaLista(listaId, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/alterarLista/{listaId}/perfil/{usuarioId}")
    public ResponseEntity<Void> removerPerfilNaLista(@PathVariable Integer listaId, @PathVariable Integer usuarioId){
        listaService.removerPerfilNaLista(listaId, usuarioId);
        return ResponseEntity.ok().build();
    }

    // Atualizar endereço
    @PutMapping("/alterarLista/{id}")
    public ResponseEntity<ListaDTO> atualizar(@PathVariable Integer id, @RequestBody ListaDTO listaAtualizada) {
        return ResponseEntity.ok(listaService.atualizar(id, listaAtualizada));
    }

    // Deletar endereço
    @DeleteMapping("/excluirLista/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return ResponseEntity.ok(listaService.excluir(id));
    }
}
