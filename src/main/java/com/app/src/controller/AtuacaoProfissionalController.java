package com.app.src.controller;

import com.app.src.model.AtuacaoProfissional;
import com.app.src.repository.AtuacaoProfissionalRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/atuacoesProfissionais")
public class AtuacaoProfissionalController {
    
    @Autowired
    private AtuacaoProfissionalRepository atuacaoProfissionalRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping
    public List<AtuacaoProfissional> listarTodos() {
        return atuacaoProfissionalRepository.findAll();
    }

    // Buscar endereço por ID
    @GetMapping("/{id}")
    public AtuacaoProfissional buscarPorId(@PathVariable Integer id) {
        return atuacaoProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuação Profissional não encontrado com id: " + id));
    }

    // Criar novo endereço
    @PostMapping
    public AtuacaoProfissional criar(@RequestBody AtuacaoProfissional atuacaoProfissional) {
        // Validação simples: verificar se o pesquisador existe
        if (!pesquisadorRepository.existsById(atuacaoProfissional.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + atuacaoProfissional.getPesquisador().getId());
        }
        return atuacaoProfissionalRepository.save(atuacaoProfissional);
    }

    // Atualizar endereço
    @PutMapping("/{id}")
    public AtuacaoProfissional atualizar(@PathVariable Integer id, @RequestBody AtuacaoProfissional atuacaoProfissionalAtualizada) {
        AtuacaoProfissional atuacaoProfissional = atuacaoProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuação Profissional não encontrada com id: " + id));

        atuacaoProfissional.setInstituicao(atuacaoProfissionalAtualizada.getInstituicao());
        atuacaoProfissional.setVinculo(atuacaoProfissionalAtualizada.getVinculo());
        atuacaoProfissional.setDepartamento(atuacaoProfissionalAtualizada.getDepartamento());
        atuacaoProfissional.setCargo(atuacaoProfissionalAtualizada.getCargo());
        atuacaoProfissional.setAnoInicio(atuacaoProfissionalAtualizada.getAnoInicio());
        atuacaoProfissional.setAnoFim(atuacaoProfissionalAtualizada.getAnoFim());
        atuacaoProfissional.setDestaque(atuacaoProfissionalAtualizada.getDestaque());

        return atuacaoProfissionalRepository.save(atuacaoProfissional);
    }

    // Deletar endereço
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!atuacaoProfissionalRepository.existsById(id)) {
            throw new NoSuchElementException("Atuação Profissional não encontrado com id: " + id);
        }
        atuacaoProfissionalRepository.deleteById(id);
    }
}
