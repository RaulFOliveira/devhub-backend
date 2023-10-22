package com.devhub.api.domain.publicacao;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CurrentTimestamp;

public record CreatePublicacaoData(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao
) {
}
