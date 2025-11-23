package com.app.src.dto;

public record EnderecoSemPesquisadorDTO(
         Integer id,
         String pais,
         String cidade,
         String bairro,
         String telefone,
         String email
) {
}
