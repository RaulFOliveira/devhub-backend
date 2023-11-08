package com.devhub.api.domain.contratante.dto;

import com.devhub.api.domain.contratante.Contratante;

public record ListContratanteDTO(Long id, String nome, String cnpj, String telefone, String email,
                                 Integer contratacoes) {
    public ListContratanteDTO(Contratante contratante) {
        this(contratante.getId(), contratante.getNome(), contratante.getCnpj(), contratante.getTelefone(), contratante.getEmail(), contratante.getContratacoes());
    }
}
