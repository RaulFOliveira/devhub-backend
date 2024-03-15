package com.devhub.api.domain.freelancer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
public class FreelancerValidacaoDTO {
    private String email;
    private String cpf;
    private String telefone;

    public FreelancerValidacaoDTO(String email, String cpf, String telefone) {
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
    }
}
