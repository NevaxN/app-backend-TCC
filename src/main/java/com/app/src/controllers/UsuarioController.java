package com.app.src.controllers;

import com.app.src.dto.UsuarioDTO;
import com.app.src.mappers.UsuarioMapper;
import com.app.src.models.Usuario;
import com.app.src.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar usuário
    @PostMapping("/salvarUsuario")
    public UsuarioDTO criar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(salvo);
    }

    // Listar todos os usuários
    @GetMapping("/listarUsuarios")
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar usuário por ID
    @GetMapping("/listarUsuario/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        return UsuarioMapper.toDTO(usuario);
    }

    // Atualizar usuário
    @PutMapping("/alterarUsuario/{id}")
    public UsuarioDTO atualizar(@PathVariable Integer id, @RequestBody Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        usuarioExistente.setLogin(dadosAtualizados.getLogin());
        usuarioExistente.setPassword(dadosAtualizados.getPassword());

        Usuario salvo = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toDTO(salvo);
    }

    // Deletar usuário
    @DeleteMapping("/excluirUsuario/{id}")
    public String deletar(@PathVariable Integer id) {
        if(!usuarioRepository.existsById(id)){
            return "Usuário deletado com sucesso";
        }
        usuarioRepository.deleteById(id);
        return "Usuário deletado com sucesso";
    }
}
