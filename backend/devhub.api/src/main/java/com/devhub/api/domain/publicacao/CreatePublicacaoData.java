package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.List;

public record CreatePublicacaoData(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotNull
        List<EspecialidadeDesejadaData> especialidadesDesejadas
) {
}
