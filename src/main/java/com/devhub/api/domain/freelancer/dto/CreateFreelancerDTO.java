package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.especialidade.EspecialidadeDTO;
import com.devhub.api.domain.funcao.Funcao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CreateFreelancerDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String senha,
        @NotNull
        Funcao funcao,
        @NotNull
        Double valorHora,
        @NotBlank
        String descricao,
        @NotBlank
        String senioridade
) {
}
