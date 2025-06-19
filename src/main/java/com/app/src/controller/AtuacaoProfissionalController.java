package com.app.src.controller;

import com.app.src.model.AtuacaoProfissional;
import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.mapper.AtuacaoProfissionalMapper;
import com.app.src.repository.AtuacaoProfissionalRepository;
import com.app.src.repository.PesquisadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atuacoesProfissionais")
public class AtuacaoProfissionalController {
    
    @Autowired
    private AtuacaoProfissionalRepository atuacaoProfissionalRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarAtuacaoesProfissionais")
    public List<AtuacaoProfissionalDTO> listarTodos() {
        return atuacaoProfissionalRepository.findAll().stream()
                .map(AtuacaoProfissionalMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID
    @GetMapping("/listarAtuacaoProfissional/{id}")
    public AtuacaoProfissionalDTO buscarPorId(@PathVariable Integer id) {
        AtuacaoProfissional atuacaoProfissional = atuacaoProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuação Profissional não encontrado com id: " + id));
        
        return AtuacaoProfissionalMapper.toDTO(atuacaoProfissional);
    }

    // Criar novo endereço
    @PostMapping("/salvarAtuacaoProfissional")
    public AtuacaoProfissionalDTO criar(@RequestBody AtuacaoProfissionalDTO atuacaoProfissionalDTO) {
        AtuacaoProfissional atuacaoProfissional = AtuacaoProfissionalMapper.toEntity(atuacaoProfissionalDTO);
        
        if (atuacaoProfissional.getPesquisador() == null || atuacaoProfissional.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }
    
        if (!pesquisadorRepository.existsById(atuacaoProfissional.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + atuacaoProfissional.getPesquisador().getId());
        }

        AtuacaoProfissional salvo = atuacaoProfissionalRepository.save(atuacaoProfissional);
        
        return AtuacaoProfissionalMapper.toDTO(salvo); 
    }

    // Atualizar endereço
    @PutMapping("/alterarAtuacaoProfissional/{id}")
    public AtuacaoProfissionalDTO atualizar(@PathVariable Integer id, @RequestBody AtuacaoProfissional atuacaoProfissionalAtualizada) {
        AtuacaoProfissional atuacaoProfissional = atuacaoProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuação Profissional não encontrada com id: " + id));

        atuacaoProfissional.setInstituicao(atuacaoProfissionalAtualizada.getInstituicao());
        atuacaoProfissional.setVinculo(atuacaoProfissionalAtualizada.getVinculo());
        atuacaoProfissional.setDepartamento(atuacaoProfissionalAtualizada.getDepartamento());
        atuacaoProfissional.setCargo(atuacaoProfissionalAtualizada.getCargo());
        atuacaoProfissional.setAnoInicio(atuacaoProfissionalAtualizada.getAnoInicio());
        atuacaoProfissional.setAnoFim(atuacaoProfissionalAtualizada.getAnoFim());
        atuacaoProfissional.setDestaque(atuacaoProfissionalAtualizada.getDestaque());

        AtuacaoProfissional salvo = atuacaoProfissionalRepository.save(atuacaoProfissional);

        return AtuacaoProfissionalMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirAtuacaoProfissional/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!atuacaoProfissionalRepository.existsById(id)) {
            throw new NoSuchElementException("Atuação Profissional não encontrado com id: " + id);
        }
        atuacaoProfissionalRepository.deleteById(id);
    }
}
