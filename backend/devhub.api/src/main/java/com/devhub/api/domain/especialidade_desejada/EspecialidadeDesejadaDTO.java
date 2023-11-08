package com.devhub.api.domain.especialidade_desejada;

import jakarta.validation.constraints.NotBlank;

public record EspecialidadeDesejadaDTO(
        @NotBlank
        String nome
) {
}
