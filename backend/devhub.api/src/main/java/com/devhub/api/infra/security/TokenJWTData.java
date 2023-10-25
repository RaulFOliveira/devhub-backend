package com.devhub.api.infra.security;

public record TokenJWTData(String token, Long id, String nome, String email) {
}
