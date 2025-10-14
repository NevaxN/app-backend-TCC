package com.app.src.services;

import com.app.src.dto.PesquisaDTO;
import com.app.src.models.Pesquisador;
import com.app.src.models.Empresa;
import com.app.src.models.Tag;
import com.app.src.repositories.PesquisadorRepository;
import com.app.src.repositories.EmpresaRepository;
import com.app.src.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesquisaService {

    @Autowired
    private PesquisadorRepository pesquisadorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<PesquisaDTO> pesquisar(String termo, String tipo) {
        List<PesquisaDTO> resultados = new ArrayList<>();
        
        if (termo == null || termo.trim().isEmpty()) {
            return resultados;
        }
        
        String termoLower = termo.toLowerCase().trim();

        if (tipo == null || "todos".equals(tipo) || "pesquisador".equals(tipo)) {
            resultados.addAll(pesquisarPesquisadores(termoLower));
        }

        if (tipo == null || "todos".equals(tipo) || "empresa".equals(tipo)) {
            resultados.addAll(pesquisarEmpresas(termoLower));
        }

        return resultados;
    }

    private List<PesquisaDTO> pesquisarPesquisadores(String termo) {
        List<PesquisaDTO> resultados = new ArrayList<>();
        List<Pesquisador> todosPesquisadores = pesquisadorRepository.findAll();
        
        for (Pesquisador pesquisador : todosPesquisadores) {
            if (correspondeAPesquisa(pesquisador, termo)) {
                resultados.add(toPesquisaDTO(pesquisador));
            }
        }
        
        return resultados;
    }

    private List<PesquisaDTO> pesquisarEmpresas(String termo) {
        List<PesquisaDTO> resultados = new ArrayList<>();
        List<Empresa> todasEmpresas = empresaRepository.findAll();
        
        for (Empresa empresa : todasEmpresas) {
            if (correspondeAPesquisa(empresa, termo)) {
                resultados.add(toPesquisaDTO(empresa));
            }
        }
        
        return resultados;
    }

    private boolean correspondeAPesquisa(Pesquisador pesquisador, String termo) {
        // Pesquisa no nome
        if ((pesquisador.getNomePesquisador() != null && pesquisador.getNomePesquisador().toLowerCase().contains(termo)) ||
            (pesquisador.getSobrenome() != null && pesquisador.getSobrenome().toLowerCase().contains(termo)) ||
            (pesquisador.getNomeCitacoesBibliograficas() != null && pesquisador.getNomeCitacoesBibliograficas().toLowerCase().contains(termo))) {
            return true;
        }

        // Pesquisa nas tags
        List<Tag> tagsPesquisador = tagRepository.findAll().stream()
            .filter(t -> t.getPesquisador() != null && t.getPesquisador().getId().equals(pesquisador.getId()))
            .collect(Collectors.toList());

        for (Tag tag : tagsPesquisador) {
            if (tag.getListaTags() != null) {
                for (String tagItem : tag.getListaTags()) {
                    if (tagItem.toLowerCase().contains(termo)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean correspondeAPesquisa(Empresa empresa, String termo) {
        return (empresa.getNomeComercial() != null && empresa.getNomeComercial().toLowerCase().contains(termo)) ||
               (empresa.getNomeRegistro() != null && empresa.getNomeRegistro().toLowerCase().contains(termo)) ||
               (empresa.getSetor() != null && empresa.getSetor().toLowerCase().contains(termo)) ||
               (empresa.getFrase() != null && empresa.getFrase().toLowerCase().contains(termo)) ||
               (empresa.getTextoEmpresa() != null && empresa.getTextoEmpresa().toLowerCase().contains(termo));
    }

    private PesquisaDTO toPesquisaDTO(Pesquisador pesquisador) {
        PesquisaDTO dto = new PesquisaDTO();
        dto.setId(pesquisador.getId());
        dto.setNome(pesquisador.getNomePesquisador() + " " + (pesquisador.getSobrenome() != null ? pesquisador.getSobrenome() : ""));
        dto.setTipo("pesquisador");
        dto.setArea(pesquisador.getNomeCitacoesBibliograficas() != null ? pesquisador.getNomeCitacoesBibliograficas() : "Pesquisador");
        
        // Buscar tags do pesquisador
        List<Tag> tags = tagRepository.findAll().stream()
            .filter(t -> t.getPesquisador() != null && t.getPesquisador().getId().equals(pesquisador.getId()))
            .collect(Collectors.toList());
        
        List<String> todasTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getListaTags() != null) {
                todasTags.addAll(tag.getListaTags());
            }
        }
        dto.setTags(todasTags);
        
        return dto;
    }

    private PesquisaDTO toPesquisaDTO(Empresa empresa) {
        PesquisaDTO dto = new PesquisaDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNomeComercial());
        dto.setTipo("empresa");
        dto.setArea(empresa.getSetor() != null ? empresa.getSetor() : "Empresa");
        
        List<String> tags = new ArrayList<>();
        if (empresa.getSetor() != null) tags.add(empresa.getSetor());
        if (empresa.getFrase() != null) tags.add(empresa.getFrase());
        dto.setTags(tags);
        
        return dto;
    }
}