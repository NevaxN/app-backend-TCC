package com.app.src.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.src.models.Artigo;
import com.app.src.models.Capitulo;
import com.app.src.models.Livro;
import com.app.src.models.Pesquisador;
import com.app.src.models.TrabalhoEvento;
import com.app.src.repositories.ArtigoRepository;
import com.app.src.repositories.CapituloRepository;
import com.app.src.repositories.LivroRepository;
import com.app.src.repositories.TrabalhoEventoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProducaoBibliograficaService {

    @Autowired
    TrabalhoEventoRepository trabalhoEventoRepository;

    @Autowired
    ArtigoRepository artigoRepository;

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    CapituloRepository capituloRepository;

    final ObjectMapper mapper = new ObjectMapper();

    private static final String MISSING_STRING_VALUE = "Não informado";
    private static final int MISSING_INTEGER_VALUE = 0;
    
    public void converterJsonParaProducaoBibliografica (String jsonBody, Pesquisador pesquisador) {
        try {
            JsonNode root = mapper.readTree(jsonBody);
            JsonNode dados = root.get("dados_pesquisador").get("producoes_bibliograficas");

            // Eventos
            List<TrabalhoEvento> trabalhoEventosList = new ArrayList<>();
            JsonNode trabalhoEventos = dados.get("eventos");

            for (JsonNode te : trabalhoEventos) {
                TrabalhoEvento trabalhoEvento = new TrabalhoEvento();

                trabalhoEvento.setPesquisador(pesquisador);
                trabalhoEvento.setTitulo(getValue(te, "titulo", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setNomeEvento(getValue(te, "nome_evento", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setCidadeEvento(getValue(te, "cidade", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setClassificacaoEvento(getValue(te, "classificacao", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setAutores(getValue(te, "autores", MISSING_STRING_VALUE, String.class));
                trabalhoEvento.setSequenciaProducao(getValue(te, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                trabalhoEvento.setAno(getValue(te, "ano", MISSING_INTEGER_VALUE, Integer.class));
                trabalhoEvento.setDestaque(false);

                trabalhoEventosList.add(trabalhoEvento);
            }

            // Artigos
            List<Artigo> artigosList = new ArrayList<>();
            JsonNode artigos = dados.get("artigos");

            for (JsonNode a : artigos) {
                Artigo artigo = new Artigo();

                artigo.setPesquisador(pesquisador);
                artigo.setDoi(getValue(a, "doi", MISSING_STRING_VALUE, String.class));
                artigo.setTitulo(getValue(a, "titulo", MISSING_STRING_VALUE, String.class));
                artigo.setPeriodico(getValue(a, "periodico", MISSING_STRING_VALUE, String.class));
                artigo.setIdioma(getValue(a, "idioma", MISSING_STRING_VALUE, String.class));
                artigo.setAutores(getValue(a, "autores", MISSING_STRING_VALUE, String.class));
                artigo.setSequenciaProducao(getValue(a, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                artigo.setAno(getValue(a, "ano", MISSING_INTEGER_VALUE, Integer.class));
                artigo.setDestaque(false);

                artigosList.add(artigo);
            }

            // Livros
            List<Livro> livroList = new ArrayList<>();
            JsonNode livros = dados.get("livros");

            for (JsonNode l: livros) {
                Livro livro = new Livro();

                livro.setPesquisador(pesquisador);
                livro.setTitulo(getValue(l, "titulo", MISSING_STRING_VALUE, String.class));
                livro.setEditora(getValue(l, "editora", MISSING_STRING_VALUE, String.class));
                livro.setIdioma(getValue(l, "idioma", MISSING_STRING_VALUE, String.class));
                livro.setIsbn(getValue(l, "isbn", MISSING_STRING_VALUE, String.class));
                livro.setSequenciaProducao(getValue(l, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                livro.setNumeroPaginas(getValue(l, "numero_paginas", MISSING_INTEGER_VALUE, Integer.class));
                livro.setAno(getValue(l, "ano", MISSING_INTEGER_VALUE, Integer.class));
                livro.setAutores(getValue(l, "autores", MISSING_STRING_VALUE, String.class));
                livro.setDestaque(false);

                livroList.add(livro);
            }

            // Capítulos
            List<Capitulo> capituloList = new ArrayList<>();
            JsonNode capitulos = dados.get("capitulos");

            for (JsonNode c: capitulos) {
                Capitulo capitulo = new Capitulo();

                capitulo.setPesquisador(pesquisador);
                capitulo.setTituloCapitulo(getValue(c, "titulo", MISSING_STRING_VALUE, String.class));
                capitulo.setEditora(getValue(c, "editora", MISSING_STRING_VALUE, String.class));
                capitulo.setDoi(getValue(c, "doi", MISSING_STRING_VALUE, String.class));
                capitulo.setIdioma(getValue(c, "idioma", MISSING_STRING_VALUE, String.class));
                capitulo.setNomeLivro(getValue(c, "nome_livro", MISSING_STRING_VALUE, String.class));
                capitulo.setPaginaInicial(getValue(c, "pagina_inicial", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setPaginaFinal(getValue(c, "pagina_final", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setAno(getValue(c, "ano", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setSequenciaProducao(getValue(c, "sequencia", MISSING_INTEGER_VALUE, Integer.class));
                capitulo.setAutores(getValue(c, "autores", MISSING_STRING_VALUE, String.class));
                capitulo.setDestaque(false);

                capituloList.add(capitulo);
            }

            trabalhoEventoRepository.saveAll(trabalhoEventosList);
            artigoRepository.saveAll(artigosList);
            livroRepository.saveAll(livroList);
            capituloRepository.saveAll(capituloList);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Produção Bibliográfica", e);
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
