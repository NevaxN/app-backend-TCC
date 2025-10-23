package com.app.src.dto;

import java.util.List;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Integer id;
    private PesquisadorDTO pesquisador;
    private List<String> listaTags;
}
