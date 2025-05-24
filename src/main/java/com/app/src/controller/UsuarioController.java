package com.app.src.controller;

import com.app.src.model.Usuario;
import com.app.src.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar usuário
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Integer id, @RequestBody Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        usuarioExistente.setLogin(dadosAtualizados.getLogin());
        usuarioExistente.setPassword(dadosAtualizados.getPassword());

        return usuarioRepository.save(usuarioExistente);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        if(!usuarioRepository.existsById(id)){
            return "Usuário deletado com sucesso";
        }
        usuarioRepository.deleteById(id);
        return "Usuário deletado com sucesso";
    }
}
