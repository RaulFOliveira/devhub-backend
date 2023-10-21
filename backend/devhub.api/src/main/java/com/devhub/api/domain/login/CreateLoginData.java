package com.devhub.api.domain.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateLoginData(
        @Email
        String email,
        @NotBlank
        String senha
) {
}
