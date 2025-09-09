package com.app.src.controllers;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.mappers.FormacaoAcademicaMapper;
import com.app.src.models.FormacaoAcademica;
import com.app.src.repositories.FormacaoAcademicaRepository;
import com.app.src.repositories.PesquisadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formacoes")
public class FormacaoAcademicaController {

    @Autowired
    private FormacaoAcademicaRepository formacaoAcademicaRepository;

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @GetMapping("/listarFormacoes")
    public List<FormacaoAcademicaDTO> listarTodos() {
        return formacaoAcademicaRepository.findAll().stream()
                .map(FormacaoAcademicaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar formação por ID
    @GetMapping("/listarFormacao/{id}")
    public FormacaoAcademicaDTO buscarPorId(@PathVariable Integer id) {
        FormacaoAcademica formacao = formacaoAcademicaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com id: " + id));
            
        return FormacaoAcademicaMapper.toDTO(formacao);
    }

    // Criar nova formação
    @PostMapping("/salvarFormacao")
    public FormacaoAcademicaDTO criar(@RequestBody FormacaoAcademicaDTO formacaoDTO) {
        FormacaoAcademica graduacao = FormacaoAcademicaMapper.toEntity(formacaoDTO);

        if (graduacao.getPesquisador() == null || graduacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(graduacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + graduacao.getPesquisador().getId());
        }

        FormacaoAcademica salvo = formacaoAcademicaRepository.save(graduacao);

        return FormacaoAcademicaMapper.toDTO(salvo);
    }

    // Atualizar formação
    @PutMapping("/alterarFormacao/{id}")
    public FormacaoAcademicaDTO atualizar(@PathVariable Integer id, @RequestBody FormacaoAcademica graduacaoAtualizada) {
        FormacaoAcademica graduacao = formacaoAcademicaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com ID: " + id));

        graduacao.setNivel(graduacaoAtualizada.getNivel());
        graduacao.setInstituicao(graduacaoAtualizada.getInstituicao());
        graduacao.setCurso(graduacaoAtualizada.getCurso());
        graduacao.setStatus(graduacaoAtualizada.getStatus());
        graduacao.setAnoInicio(graduacaoAtualizada.getAnoInicio());
        graduacao.setAnoConclusao(graduacaoAtualizada.getAnoConclusao());
        graduacao.setTituloTrabalho(graduacaoAtualizada.getTituloTrabalho());
        graduacao.setOrientador(graduacaoAtualizada.getOrientador());
        graduacao.setDestaque(graduacaoAtualizada.getDestaque());

        FormacaoAcademica salvo = formacaoAcademicaRepository.save(graduacao);
        
        return FormacaoAcademicaMapper.toDTO(salvo);
    }

    // Deletar endereço
    @DeleteMapping("/excluirFormacao/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!formacaoAcademicaRepository.existsById(id)) {
            throw new NoSuchElementException("Formação não encontrada com ID: " + id);
        }
        formacaoAcademicaRepository.deleteById(id);
    }
    
}
