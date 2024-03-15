package com.devhub.api.domain.contratante.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ContratanteValidacaoDTO {
    private String email;
    private String cnpj;
    private String telefone;

    public ContratanteValidacaoDTO(String email, String cnpj, String telefone) {
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
}
