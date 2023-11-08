package com.devhub.api.domain.publicacao.dto;

import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.publicacao.Publicacao;

import java.time.LocalDateTime;
import java.util.List;

public record DetailPublicacaoDTO(Long id, String titulo, String descricao, LocalDateTime createdAt, List<EspecialidadeDesejada> especialidadesDesejadas) {

    public DetailPublicacaoDTO(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(), publicacao.getDescricao(), publicacao.getCreatedAt(), publicacao.getEspecialidadesDesejadas());
    }
}
