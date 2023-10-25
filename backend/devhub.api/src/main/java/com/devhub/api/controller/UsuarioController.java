package com.devhub.api.controller;

import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            // TODO: validar os dados do sessionStorage pra requests - var teste = ((Usuario) auth.getPrincipal()).getEmail();
            return ResponseEntity.ok(new TokenJWTData(token, ((Usuario) auth.getPrincipal()).getId(), ((Usuario) auth.getPrincipal()).getNome(), ((Usuario) auth.getPrincipal()).getEmail()));
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(404).body("Email e/ou senha inválidos");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

//    @PostMapping("/logout")
//    public ResponseEntity logout(@RequestBody @Valid TokenJWTData tokenData) {
//        // Adicione o token à lista negra (pode ser um banco de dados, cache, etc.)
//        tokenBlacklistService.addToBlacklist(tokenData.getToken());
//
//        return ResponseEntity.ok("Logout realizado com sucesso");
//    }

}
