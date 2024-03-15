package com.devhub.api.domain.contratante.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;


public record CreateContratanteDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CNPJ
        String cnpj,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @Email
        String email,
        @NotBlank
        String senha
) {
}
