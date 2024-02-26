package com.devhub.api.domain.publicacao.dto;

import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.usuario.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePublicacaoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        String role,

        Long id_usuario
) {

}
