package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.especialidades.EspecialidadesData;
import com.devhub.api.domain.funcao.Funcao;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record CreateFreelancerData(
        @NotBlank
        String nome,
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @Email
        String email,
        @NotNull
        Funcao funcao,
        @NotBlank
        Double valorHora,
        @NotNull
        @Valid
        EspecialidadesData especialidades,
        @NotBlank
        String descricao



) {
}
