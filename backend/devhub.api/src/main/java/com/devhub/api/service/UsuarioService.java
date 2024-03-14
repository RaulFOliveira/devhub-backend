package com.devhub.api.service;

import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService jwtTokenProvider;

    public TokenJWTData login(AutenticacaoData loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(),loginRequest.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = jwtTokenProvider.gerarToken((Usuario) auth.getPrincipal());
        var dadosUser = (Usuario) auth.getPrincipal();

        return new TokenJWTData(token, dadosUser.getId(), dadosUser.getNome(), dadosUser.getEmail(),dadosUser.getRole(), dadosUser.getImagem());
    }
}
