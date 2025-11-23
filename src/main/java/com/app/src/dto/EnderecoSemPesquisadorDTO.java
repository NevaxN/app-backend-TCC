package com.app.src.dto;

import com.app.src.models.Pesquisador;

public record EnderecoSemPesquisadorDTO(
         Integer id,
         String pais,
         String cidade,
         String bairro,
         String telefone,
         String email
) {
}
