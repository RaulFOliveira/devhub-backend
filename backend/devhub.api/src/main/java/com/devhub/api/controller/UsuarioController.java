package com.devhub.api.controller;

import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import com.devhub.api.service.AuthorizationService;
import com.devhub.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AutenticacaoData loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }

}
