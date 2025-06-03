package com.app.src.controller;

import com.app.src.model.Endereco;
import com.app.src.repository.EnderecoRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping
    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Endereco buscarPorId(@PathVariable Integer id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Endereco criar(@RequestBody Endereco endereco) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(endereco.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + endereco.getPesquisador().getId());
        }
        return enderecoRepository.save(endereco);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Endereco atualizar(@PathVariable Integer id, @RequestBody Endereco enderecoAtualizado) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com id: " + id));

        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEmail(enderecoAtualizado.getEmail());
        endereco.setPais(enderecoAtualizado.getPais());
        endereco.setTelefone(enderecoAtualizado.getTelefone());

        return enderecoRepository.save(endereco);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new NoSuchElementException("Endereço não encontrado com id: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
