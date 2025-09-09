package com.app.src.dto;

import com.app.src.models.Pesquisador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormacaoAcademicaDTO {
    
    private Integer id;
    
    private Pesquisador pesquisador;
    
    private String nivel;

    private int sequenciaFormacao;
    
    private String instituicao;
    
    private String curso;
    
    private String status;
    
    private Integer anoInicio;
    
    private Integer anoConclusao;
    
    private String tituloTrabalho;
    
    private String orientador;
    
    private Boolean destaque;
}
