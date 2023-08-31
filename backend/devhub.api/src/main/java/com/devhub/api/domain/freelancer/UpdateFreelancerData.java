package com.devhub.api.domain.freelancer;

import jakarta.validation.constraints.NotNull;

public record UpdateFreelancerData(@NotNull Long id ,String nome, String telefone, String senha, String descricao, Double valorHora) {

}
