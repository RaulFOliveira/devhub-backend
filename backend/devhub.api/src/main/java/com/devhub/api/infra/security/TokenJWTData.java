package com.devhub.api.infra.security;

import com.devhub.api.domain.usuario.UserRole;

public record TokenJWTData(String token, Long id, String nome, String email, UserRole role, byte[] imagem) {
}
