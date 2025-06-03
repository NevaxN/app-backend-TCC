package com.app.src.controller;

import com.app.src.model.Empresa;
import com.app.src.repository.EmpresaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
        
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<Empresa> listarTodos() {
        return empresaRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Integer id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public Empresa criar(@RequestBody Empresa empresa) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(empresa.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + empresa.getPesquisador().getId());
        }
        return empresaRepository.save(empresa);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public Empresa atualizar(@PathVariable Integer id, @RequestBody Empresa empresaAtualizada) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        empresa.setNome(empresaAtualizada.getNome());

        return empresaRepository.save(empresa);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new NoSuchElementException("Empresa não encontrado com id: " + id);
        }
        empresaRepository.deleteById(id);
    }
}
