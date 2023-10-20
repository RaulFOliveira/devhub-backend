package com.devhub.api.domain.login;

import com.devhub.api.domain.freelancer.Freelancer;

public record DetailLoginData(Long id, String email, String nome) {
    public DetailLoginData(Login login) {
        this(login.getId(),
                login.getEmail(),
                login.getSenha());
    }
}
