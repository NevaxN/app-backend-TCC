package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.src.dto.AtuacaoProfissionalDTO;
import com.app.src.mappers.AtuacaoProfissionalMapper;
import com.app.src.models.AtuacaoProfissional;
import com.app.src.models.Pesquisador;
import com.app.src.models.ProjetoPesquisa;
import com.app.src.repositories.AtuacaoProfissionalRepository;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.ProjetoPesquisaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AtuacaoProfissionalService extends 
GenericCrudService<AtuacaoProfissional, AtuacaoProfissionalDTO, Integer, AtuacaoProfissionalRepository>{
    
    @Autowired
    AtuacaoProfissionalRepository atuacaoProfissionalRepository;

    @Autowired
    PesquisadorRepository pesquisadorRepository;

    @Autowired
    ProjetoPesquisaRepository projetoPesquisaRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;

    public AtuacaoProfissionalService(AtuacaoProfissionalRepository repository, AtuacaoProfissionalMapper mapper){
        super(repository, mapper);
    }

    @Override
    @Cacheable(value = "atuacoesProfissionais", key = "#id")
    public AtuacaoProfissionalDTO buscarPorId(Integer id){
        return super.buscarPorId(id);
    }

    @Override
    public AtuacaoProfissionalDTO salvar(AtuacaoProfissionalDTO atuacaoProfissionalDTO){
        AtuacaoProfissional atuacaoProfissional = mapper.toEntity(atuacaoProfissionalDTO);
        
        if (atuacaoProfissional.getPesquisador() == null || atuacaoProfissional.getPesquisador().getId() == null) {
            throw new IllegalArgumentException("ID do pesquisador é obrigatório.");
        }
    
        if (!pesquisadorRepository.existsById(atuacaoProfissional.getPesquisador().getId())) {
            throw new NoSuchElementException("Pesquisador não encontrado com id: " + atuacaoProfissional.getPesquisador().getId());
        }
        
        return super.salvar(atuacaoProfissionalDTO);
    }

    public AtuacaoProfissionalDTO atualizar(Integer id, AtuacaoProfissionalDTO atuacaoProfissionalAtualizada){
        AtuacaoProfissional atuacaoProfissional = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AtuacaoProfissional não encontrado com id: " + id));

        ((AtuacaoProfissionalMapper) mapper).updateEntityFromDto(atuacaoProfissionalAtualizada, atuacaoProfissional);

        AtuacaoProfissional salvo = atuacaoProfissionalRepository.save(atuacaoProfissional);

        return mapper.toDTO(salvo);
    }
    
    public void converterJsonParaAtuacaoProfissional (String jsonBody, Pesquisador pesquisador) {
        try {

            List<AtuacaoProfissional> atuacaoProfissionalList = new ArrayList<>();
            List<ProjetoPesquisa> projetoPesquisaList = new ArrayList<>();

            JsonNode root = objectMapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("atuacoes_profissionais");

            for (JsonNode ap: dados) {
                String instituicao = getValue(ap, "instituicao", MISSING_STRING_VALUE, String.class);
                Integer sequenciaAtuacao = getValue(ap, "sequencia_atuacao", MISSING_INTEGER_VALUE, Integer.class);

                JsonNode vinculos = ap.get("vinculos");

                if (vinculos != null && vinculos.isArray()) {
                    for (JsonNode v : vinculos) {
                        AtuacaoProfissional atuacaoProfissional = new AtuacaoProfissional();

                        atuacaoProfissional.setPesquisador(pesquisador);
                        atuacaoProfissional.setInstituicao(instituicao);
                        atuacaoProfissional.setSequenciaAtuacao(sequenciaAtuacao);

                        atuacaoProfissional.setCargo(getValue(v, "cargo", MISSING_STRING_VALUE, String.class));
                        atuacaoProfissional.setSequenciaVinculo(getValue(v, "sequencia_vinculo", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setMesInicio(getValue(v, "mes_inicio", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setMesFim(getValue(v, "mes_fim", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setAnoInicio(getValue(v, "ano_inicio", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setAnoFim(getValue(v, "ano_fim", MISSING_INTEGER_VALUE, Integer.class));
                        atuacaoProfissional.setDestaque(false);

                        atuacaoProfissionalList.add(atuacaoProfissional);
                    }
                }

                JsonNode projetosPesquisa = ap.get("projetos_de_pesquisa");
                if (projetosPesquisa != null && projetosPesquisa.isArray()) {
                    for (JsonNode p: projetosPesquisa) {
                        ProjetoPesquisa projetoPesquisa = new ProjetoPesquisa();

                        projetoPesquisa.setPesquisador(pesquisador);
                        projetoPesquisa.setTitulo(getValue(p, "titulo", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setFinanciador(getValue(p, "financiador", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setDescricao(getValue(p, "descricao", MISSING_STRING_VALUE, String.class));
                        projetoPesquisa.setAno(getValue(p, "ano", MISSING_INTEGER_VALUE, Integer.class));
                        projetoPesquisa.setSequencia(getValue(p, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                        projetoPesquisa.setInstituicao(instituicao);
                        projetoPesquisa.setDestaque(false);

                        projetoPesquisaList.add(projetoPesquisa);
                    }
                }
            }

            atuacaoProfissionalRepository.saveAll(atuacaoProfissionalList);
            projetoPesquisaRepository.saveAll(projetoPesquisaList);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Atuação Profissional", e);
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
