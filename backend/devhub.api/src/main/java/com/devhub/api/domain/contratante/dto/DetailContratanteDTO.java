package com.devhub.api.domain.contratante.dto;

import com.devhub.api.domain.contratante.Contratante;

public record DetailContratanteDTO(Long id_contratante, String nome, String cnpj, String telefone, String email,
                                   Integer contratacoes, Boolean ativo) {

    public DetailContratanteDTO(Contratante contratante) {
        this(
                contratante.getId(),
                contratante.getNome(),
                contratante.getCnpj(),
                contratante.getTelefone(),
                contratante.getEmail(),
                contratante.getContratacoes(),
                contratante.getAtivo());
    }
}
