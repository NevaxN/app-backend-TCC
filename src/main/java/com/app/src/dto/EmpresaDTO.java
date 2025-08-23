package com.app.src.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {
    
    private Integer id;
    
    private String nomeRegistro;

    private String nomeComercial;

    private String cnpj;

    private String numeroEndereco;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private String telefone;

    private String email;

    private String site;

    private String setor;

    private String frase;

    private String textoEmpresa;
}
