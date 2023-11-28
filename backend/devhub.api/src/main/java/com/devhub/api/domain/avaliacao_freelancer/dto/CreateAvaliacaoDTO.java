package com.devhub.api.domain.avaliacao_freelancer.dto;

import com.devhub.api.domain.usuario.UserRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateAvaliacaoDTO (
        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        Double nota

        ){
}
