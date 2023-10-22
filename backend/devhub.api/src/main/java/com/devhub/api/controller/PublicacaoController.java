package com.devhub.api.controller;

import com.devhub.api.domain.freelancer.DetailFreelancerData;
import com.devhub.api.domain.publicacao.CreatePublicacaoData;
import com.devhub.api.domain.publicacao.DetailPublicacaoData;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder.*;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity criarPublicacao(@RequestBody @Valid CreatePublicacaoData data, UriComponentsBuilder uriBuilder) {
        var publicacao = new Publicacao(data);

        repository.save(publicacao);
        var uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(publicacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailPublicacaoData(publicacao));
    }
}
