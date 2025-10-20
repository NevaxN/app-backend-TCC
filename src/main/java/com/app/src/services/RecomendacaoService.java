package com.app.src.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.src.models.Pesquisador;
import com.app.src.models.Seguidor;
import com.app.src.models.Tag;
import com.app.src.repositories.TagRepository;

@Service
public class RecomendacaoService {
    
    @Autowired
    SeguidorService seguidorService;

    @Autowired
    PesquisadorService pesquisadorService;

    @Autowired
    TagRepository tagRepository;

    public List<Pesquisador> getRecomendacao(Integer usuarioId) {
        // Obter os IDs dos pesquisadores que o usuário já segue.
        List<Seguidor> seguindo = seguidorService.buscarPorUsuarioId(usuarioId);
        Set<Integer> idsPesquisadoresSeguidos = seguindo.stream()
                .map(seguidor -> seguidor.getPesquisador().getId())
                .collect(Collectors.toSet());

        // Coletar todas as tags desses pesquisadores e calcular a frequência (peso).
        Map<String, Long> frequenciaTags = idsPesquisadoresSeguidos.stream()
                .map(tagRepository::findByPesquisadorId)
                .flatMap(List::stream)
                .flatMap(tag -> tag.getListaTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (frequenciaTags.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Encontrar todos os pesquisadores que possuem alguma das tags de interesse.
        Set<String> tagsDeInteresse = frequenciaTags.keySet();
        Set<Pesquisador> pesquisadoresCandidatos = tagsDeInteresse.stream()
                .flatMap(tag -> tagRepository.findByTagContaining(tag).stream())
                .map(Tag::getPesquisador)
                .collect(Collectors.toSet());

        // Pontuar os candidatos com base na frequência das tags.
        Map<Pesquisador, Long> candidatosPontuados = pesquisadoresCandidatos.stream()
                .filter(candidato -> !idsPesquisadoresSeguidos.contains(candidato.getId()))
                .collect(Collectors.toMap(
                    Function.identity(),
                    candidato -> calcularPontuacao(candidato, frequenciaTags)
                ));

        // Ordenar os candidatos pela pontuação em ordem decrescente e retornar.
        return candidatosPontuados.entrySet().stream()
                .sorted(Map.Entry.<Pesquisador, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }

    private Long calcularPontuacao(Pesquisador pesquisador, Map<String, Long> frequenciaTags) {
        return tagRepository.findByPesquisadorId(pesquisador.getId()).stream()
                .flatMap(tag -> tag.getListaTags().stream())
                .mapToLong(tag -> frequenciaTags.getOrDefault(tag, 0L))
                .sum();
    }
}