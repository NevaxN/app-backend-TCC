package com.app.src.controllers;

import com.app.src.models.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private Map<Integer, Usuario> usuarios = new HashMap<>();
    private Integer proximoId = 1;

    // Criar usuário
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        usuario.setId(proximoId++);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios.values());
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new NoSuchElementException("Usuário não encontrado");
        }
        return usuario;
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Integer id, @RequestBody Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarios.get(id);
        if (usuarioExistente == null) {
            throw new NoSuchElementException("Usuário não encontrado");
        }

        usuarioExistente.setLogin(dadosAtualizados.getLogin());
        usuarioExistente.setPassword(dadosAtualizados.getPassword());
        return usuarioExistente;
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        Usuario removido = usuarios.remove(id);
        if (removido == null) {
            return "Usuário não encontrado";
        }
        return "Usuário deletado com sucesso";
    }
}
