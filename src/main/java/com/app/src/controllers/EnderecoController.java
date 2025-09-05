package com.app.src.controllers;

import com.app.src.dto.EnderecoDTO;
import com.app.src.mappers.EnderecoMapper;
import com.app.src.models.Endereco;
import com.app.src.repositories.EnderecoRepository;
import com.app.src.repositories.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    // Listar todos os endereços
    @GetMapping("/listarEnderecos")
    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll().stream()
                .map(EnderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEndereco/{id}")
    public EnderecoDTO buscarPorId(@PathVariable Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com id: " + id));
        return EnderecoMapper.toDTO(endereco);
    }

    // Criar novo endereço
    @PostMapping("/salvarEndereco")
    public EnderecoDTO criar(@RequestBody EnderecoDTO enderecoDTO) {
        
        Endereco endereco = EnderecoMapper.toEntity(enderecoDTO);
        
        if (endereco.getPesquisador() == null || endereco.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(endereco.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + endereco.getPesquisador().getId());
        }

        Endereco salvo = enderecoRepository.save(endereco);

        return EnderecoMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarEndereco/{id}")
    public EnderecoDTO atualizar(@PathVariable Integer id, @RequestBody Endereco enderecoAtualizado) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com id: " + id));

        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEmail(enderecoAtualizado.getEmail());
        endereco.setPais(enderecoAtualizado.getPais());
        endereco.setTelefone(enderecoAtualizado.getTelefone());

        Endereco salvo = enderecoRepository.save(endereco);

        return EnderecoMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirEndereco/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new NoSuchElementException("Endereço não encontrado com id: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}
