package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;

import java.time.LocalDate;

public record DetailPublicacaoData(Long id, String titulo, String descricao, LocalDate createdAt) {

    public DetailPublicacaoData(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(), publicacao.getDescricao(), publicacao.getCreatedAt());
    }
}
