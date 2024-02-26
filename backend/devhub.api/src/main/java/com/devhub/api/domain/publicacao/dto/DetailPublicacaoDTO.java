package com.devhub.api.domain.publicacao.dto;

import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.usuario.UserRole;

import java.time.LocalDateTime;

public record DetailPublicacaoDTO(Long id, String titulo, String descricao, LocalDateTime createdAt, String role) {

    public DetailPublicacaoDTO(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(), publicacao.getDescricao(), publicacao.getCreatedAt(), publicacao.getRole());
    }
}
