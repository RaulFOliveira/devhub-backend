package com.devhub.api.domain.servico;

import jakarta.validation.constraints.NotNull;

public record CreateServicoDTO(
                                 @NotNull(message = "As horas trabalhadas não podem estar em branco")
                                 Integer horasTrabalhadas

                                ) {
}
