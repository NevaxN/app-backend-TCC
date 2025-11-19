package com.app.src.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.src.dto.PesquisadorDTO;
import com.app.src.mappers.PesquisadorMapper;
import com.app.src.models.Pesquisador;
import com.app.src.models.Tag;
import com.app.src.repositories.TagRepository;

@Service
public class RecomendacaoService {
    
    @Autowired
    FavoritoService favoritoService;

    @Autowired
    PesquisadorService pesquisadorService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PesquisadorMapper pesquisadorMapper;

    public List<PesquisadorDTO> getRecomendacao(Integer usuarioId) {
        // Obter os IDs dos pesquisadores que o usuário já segue.
        Set<Integer> idsPesquisadoresFavoritos = favoritoService.buscarIdsPesquisadoresPorUsuarioId(usuarioId);

        // Coletar todas as tags desses pesquisadores e calcular a frequência (peso).
        Map<String, Long> frequenciaTags = idsPesquisadoresFavoritos.stream()
                .map(tagRepository::findListaByPesquisadorId)
                .filter(list -> list != null && !list.isEmpty())
                .flatMap(List::stream)
                .flatMap(tag -> tag.getListaTags().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        
        System.out.println("LOG RECOMENDAÇÃO: IDs de favoritos: " + idsPesquisadoresFavoritos);
        System.out.println("LOG RECOMENDAÇÃO: Frequência das tags: " + frequenciaTags);

        if (frequenciaTags.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> tagsDeInteresse = frequenciaTags.keySet();
        
        // Encontrar todos os pesquisadores que possuem alguma das tags de interesse.
        Set<Pesquisador> pesquisadoresCandidatos = tagsDeInteresse.stream()
                .flatMap(tag -> tagRepository.findByTagContaining(tag).stream())
                .map(Tag::getPesquisador)
                .collect(Collectors.toSet());

        // Pontuar os candidatos com base na frequência das tags.
        List<Pesquisador> listaFinal = pesquisadoresCandidatos.stream()
                .filter(candidato -> {
                        boolean naoFavorito = !idsPesquisadoresFavoritos.contains(candidato.getId());
                        boolean naoEUsuarioAtual = !candidato.getUsuario().getId().equals(usuarioId);

                        return naoFavorito && naoEUsuarioAtual;
                })
                .map(candidato -> Map.entry(
                        candidato,
                        calcularPontuacao(candidato, frequenciaTags))
                )
                .sorted(Map.Entry.<Pesquisador, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());

        // Ordenar os candidatos pela pontuação em ordem decrescente e retornar.
        return listaFinal.stream().map(pesquisadorMapper::toDTO).collect(Collectors.toList());
    }

    private Long calcularPontuacao(Pesquisador pesquisador, Map<String, Long> frequenciaTags) {
        List<Tag> tagEntities = tagRepository.findListaByPesquisadorId(pesquisador.getId()); 
        
        if (tagEntities == null || tagEntities.isEmpty()) {
            return 0L;
        }

        return tagEntities.stream()
                .flatMap(tag -> tag.getListaTags().stream()) // 1. Achatamos a List<List<Tag>> para Stream<String>
                .mapToLong(tag -> frequenciaTags.getOrDefault(tag, 0L))
                .sum();
    }
}