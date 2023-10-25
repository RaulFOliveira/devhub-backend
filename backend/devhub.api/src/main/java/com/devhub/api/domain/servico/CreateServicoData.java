package com.devhub.api.domain.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateServicoData(
                                 @NotNull(message = "As horas trabalhadas não podem estar em branco")
                                 Integer horasTrabalhadas

                                ) {
}
