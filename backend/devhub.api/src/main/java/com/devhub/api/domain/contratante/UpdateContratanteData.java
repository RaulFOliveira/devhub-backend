package com.devhub.api.domain.contratante;

import jakarta.validation.constraints.NotNull;

public record UpdateContratanteData(String nome, String telefone, String senha) {

}

