package com.devhub.api.domain.contratante.dto;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.usuario.UserRole;

public record DetailContratanteDTO(Long id, String nome, String cnpj, String telefone, String email,
                                   Integer contratacoes, UserRole role, Boolean ativo) {

    public DetailContratanteDTO(Contratante contratante) {
        this(
                contratante.getId(),
                contratante.getNome(),
                contratante.getCnpj(),
                contratante.getTelefone(),
                contratante.getEmail(),
                contratante.getContratacoes(),
                contratante.getRole(),
                contratante.getAtivo());
    }
}
