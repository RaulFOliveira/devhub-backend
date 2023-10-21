package com.devhub.api.domain.login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "login")
@Entity()
@Getter
@Setter
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String senha;

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}
