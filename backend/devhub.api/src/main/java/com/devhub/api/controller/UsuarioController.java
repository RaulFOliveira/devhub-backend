package com.devhub.api.controller;

import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.CreateUsuarioData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService jwtTokenProvider;


    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AutenticacaoData loginRequest) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(),loginRequest.senha());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = jwtTokenProvider.gerarToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new TokenJWTData(token));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid CreateUsuarioData data){
//        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
//        Usuario newUser = new Usuario(data.nome(), data.telefone(), data.email(), data.senha(), data.role()) {
//        };
//
//        this.repository.save(newUser);
//
//        return ResponseEntity.ok().build();
//    }
}
