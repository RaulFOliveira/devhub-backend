package com.devhub.api.domain.freelancer;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class FreelancerValidacaoDTO {
    private String email;
    private String cpf;
    private String telefone;
}
