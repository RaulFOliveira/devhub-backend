package com.devhub.api.domain.contratante;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContratanteValidacaoDTO {
    private String email;
    private String cnpj;
    private String telefone;
}
