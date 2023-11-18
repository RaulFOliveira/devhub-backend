package com.devhub.api.domain.publicacao.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePublicacaoAgendadaDTO(
        @NotNull
        Integer qtdPublicacoes
) {
}
