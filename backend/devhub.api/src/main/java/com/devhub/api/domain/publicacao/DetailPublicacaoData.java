package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;

import java.time.LocalDateTime;
import java.util.List;

public record DetailPublicacaoData(Long id, String titulo, String descricao, LocalDateTime createdAt, List<EspecialidadeDesejada> especialidadesDesejadas) {

    public DetailPublicacaoData(Publicacao publicacao){
        this(publicacao.getId(), publicacao.getTitulo(), publicacao.getDescricao(), publicacao.getCreatedAt(), publicacao.getEspecialidadesDesejadas());
    }
}
