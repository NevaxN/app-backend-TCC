package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.FormacaoAcademicaDTO;
import com.app.src.mappers.FormacaoAcademicaMapper;
import com.app.src.models.FormacaoAcademica;
import com.app.src.models.Pesquisador;
import com.app.src.repositories.FormacaoAcademicaRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FormacaoAcademicaService extends 
GenericCrudService<FormacaoAcademica, FormacaoAcademicaDTO, Integer, FormacaoAcademicaRepository> {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    public FormacaoAcademicaService(FormacaoAcademicaRepository repository, FormacaoAcademicaMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "formacoes", key = "#id")
    public FormacaoAcademicaDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }


    public List<FormacaoAcademicaDTO> buscarPorIdPesquisador (Integer idPesquisador) {
        List<FormacaoAcademica> formacoes = repository.findByPesquisadorId(idPesquisador);
        return formacoes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FormacaoAcademicaDTO salvar(FormacaoAcademicaDTO formacaoAcademicaDTO){
        FormacaoAcademica graduacao = mapper.toEntity(formacaoAcademicaDTO);

        if (graduacao.getPesquisador() == null || graduacao.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }

        if (!pesquisadorRepository.existsById(graduacao.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + graduacao.getPesquisador().getId());
        }

        return super.salvar(formacaoAcademicaDTO);
    }

    public FormacaoAcademicaDTO atualizar(Integer id, FormacaoAcademicaDTO dadosAtualizados){
        FormacaoAcademica graduacao = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Graduacao não encontrado com ID: " + id));

        ((FormacaoAcademicaMapper) mapper).updateEntityFromDto(dadosAtualizados, graduacao);

        FormacaoAcademica salvo = repository.save(graduacao);

        return mapper.toDTO(salvo);
    }

    public List<FormacaoAcademica> converterJsonParaFormacaoAcademica (String jsonBody, Pesquisador pesquisador) {
        try {
            List<FormacaoAcademica> formacaoAcademicaList = new ArrayList<>();

            JsonNode root = objectMapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("formacao_academica");

            for (JsonNode fa: dados) {
                FormacaoAcademica formacaoAcademica = new FormacaoAcademica();

                formacaoAcademica.setPesquisador(pesquisador);
                formacaoAcademica.setSequenciaFormacao(getValue(fa, "sequencia_formacao", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setNivel(getValue(fa, "tipo", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setCurso(getValue(fa, "nome_curso", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setInstituicao(getValue(fa, "nome_instituicao", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setStatus(getValue(fa, "status", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setAnoInicio(getValue(fa, "ano_de_inicio", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setAnoConclusao(getValue(fa, "ano_de_conclusao", MISSING_INTEGER_VALUE, Integer.class));
                formacaoAcademica.setTituloTrabalho(getValue(fa, "titulo_trabalho", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setOrientador(getValue(fa, "orientador", MISSING_STRING_VALUE, String.class));
                formacaoAcademica.setDestaque(false);

                formacaoAcademicaList.add(formacaoAcademica);
            }

            return formacaoAcademicaList;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Formação Acadêmica", e);
        }
    }

    private <T> T getValue (JsonNode node, String key, T defaultValue, Class<T> type) {
        if (node.has(key) && !node.get(key).isNull()) {
            JsonNode value = node.get(key);
            if (type == String.class) return type.cast(value.asText());
            if (type == Integer.class) return type.cast(value.asInt());
        } else {
            return defaultValue;
        };
        return defaultValue;
    }
}
