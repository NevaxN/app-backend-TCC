package com.app.src.controllers;

import com.app.src.dto.EmpresaDTO;
import com.app.src.mappers.EmpresaMapper;
import com.app.src.models.Empresa;
import com.app.src.repositories.EmpresaRepository;

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
        
        Empresa salvo = empresaRepository.save(empresa);

        return EmpresaMapper.toDTO(salvo);
    }

    // Atualizar endereço
    @PutMapping("/alterarEmpresa/{id}")
    public EmpresaDTO atualizar(@PathVariable Integer id, @RequestBody Empresa empresaAtualizada) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com id: " + id));

        empresa.setNomeRegistro(empresaAtualizada.getNomeRegistro());
        empresa.setNomeComercial(empresaAtualizada.getNomeComercial());
        empresa.setBairro(empresaAtualizada.getBairro());
        empresa.setCep(empresaAtualizada.getCep());
        empresa.setCidade(empresaAtualizada.getCidade());
        empresa.setCnpj(empresaAtualizada.getCnpj());
        empresa.setEmail(empresaAtualizada.getEmail());
        empresa.setEstado(empresaAtualizada.getEstado());
        empresa.setFrase(empresaAtualizada.getFrase());
        empresa.setNumeroEndereco(empresaAtualizada.getNumeroEndereco());
        empresa.setSetor(empresaAtualizada.getSetor());
        empresa.setSite(empresaAtualizada.getSite());
        empresa.setTelefone(empresaAtualizada.getTelefone());
        empresa.setTextoEmpresa(empresaAtualizada.getTextoEmpresa());
        empresa.setLogradouro(empresaAtualizada.getLogradouro());

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
