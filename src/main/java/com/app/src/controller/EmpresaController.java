package com.app.src.controller;

import com.app.src.model.Empresa;
import com.app.src.dto.EmpresaDTO;
import com.app.src.mapper.EmpresaMapper;
import com.app.src.repository.EmpresaRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
        
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarEmpresas")
    public List<EmpresaDTO> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(EmpresaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarEmpresa/{id}")
    public EmpresaDTO buscarPorId(@PathVariable Integer id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        return EmpresaMapper.toDTO(empresa);
    }

    // Criar novo endereço
    @PostMapping("/salvarEmpresa")
    public EmpresaDTO criar(@RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = EmpresaMapper.toEntity(empresaDTO);

        if (empresa.getPesquisador() == null || empresa.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(empresa.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + empresa.getPesquisador().getId());
        }

        Empresa salvo = empresaRepository.save(empresa);

        return EmpresaMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarEmpresa/{id}")
    public EmpresaDTO atualizar(@PathVariable Integer id, @RequestBody Empresa empresaAtualizada) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        empresa.setNome(empresaAtualizada.getNome());

        Empresa salvo = empresaRepository.save(empresa);

        return EmpresaMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirEmpresa/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new NoSuchElementException("Empresa não encontrado com id: " + id);
        }
        empresaRepository.deleteById(id);
    }
}
