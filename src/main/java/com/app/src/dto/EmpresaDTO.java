package com.app.src.dto;

import com.app.src.model.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String nome;
}
