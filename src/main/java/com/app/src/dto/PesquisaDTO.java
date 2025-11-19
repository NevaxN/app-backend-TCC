package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PesquisaDTO {
    private Integer id;
    private Integer usuarioId;
    private String nome;
    private String tipo;
    private String area;
    private List<String> tags;
    
    public PesquisaDTO() {}
    
    public PesquisaDTO(Integer id, String nome, String tipo, String area, List<String> tags) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.area = area;
        this.tags = tags;
    }
}