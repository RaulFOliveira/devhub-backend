package com.devhub.api.controller;

import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.domain.usuario.UsuarioRepository;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AutenticacaoData data) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            System.out.println("Passei da authToken: " + authenticationToken);
            var authentication = manager.authenticate(authenticationToken);
            System.out.println("Passei da auth");
            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
            System.out.println("Passei do tokenJWT");

            return ResponseEntity.ok(new TokenJWTData(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
}
