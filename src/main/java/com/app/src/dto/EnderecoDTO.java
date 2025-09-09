package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String pais;
    
    private String cidade;
    
    private String bairro;
    
    private String telefone;
    
    private String email;

}
