package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePublicacaoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotNull
        List<EspecialidadeDesejadaDTO> especialidadesDesejadas
) {
}
