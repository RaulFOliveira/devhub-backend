package com.devhub.api.domain.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateServicoData( @NotNull(message = "A duração não pode ser nula")
                                 Integer duracao,

                                 @NotBlank(message = "As horas trabalhadas não podem estar em branco")
                                 String horasTrabalhadas

                                ) {
}
