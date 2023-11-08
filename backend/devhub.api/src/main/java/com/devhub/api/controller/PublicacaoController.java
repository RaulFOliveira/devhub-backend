package com.devhub.api.controller;

import com.devhub.api.ListaObj;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.dto.DetailPublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.service.PublicacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoService service;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity criarPublicacao(@RequestBody @Valid CreatePublicacaoDTO data, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        var publicacao = service.realizarPublicacao(data, id);
        var uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(publicacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailPublicacaoDTO(publicacao));
    }

    @GetMapping
    public ResponseEntity<List<Publicacao>> mostrarPublicacoes() {
        var publicacoes = service.mostrarPublicacoes();
        return ResponseEntity.status(200).body(publicacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Publicacao>> mostrarPublicacoesById(@PathVariable Long id) {
        var publicacoes = service.mostrarPublicacoesByid(id);
        return ResponseEntity.status(200).body(publicacoes);
    }
}
