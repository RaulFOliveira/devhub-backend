package com.devhub.api.domain.publicacao.dto;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record ListaPublicacaoDTO(
    Long id,
    String nome,
    byte[] imagem,
    String descricao,
    Long id_usuario,
    LocalDateTime getCreatedAt,
    String role

){
    public ListaPublicacaoDTO(Usuario usuario, Publicacao publicacao) {
            this(publicacao.getId(), usuario.getNome(), usuario.getImagem(),
                    publicacao.getDescricao(), publicacao.getId_usuario(), publicacao.getCreatedAt(), publicacao.getRole());
        }
}
