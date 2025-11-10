package com.app.src.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Integer id;
    private Integer idPesquisador;
    private List<String> listaTags;
}
