package com.devhub.api.domain.usuario;

public record CreateUsuarioData(String nome,
                                String telefone,
                                String email,
                                String senha,
                                UserRole role) {

}
