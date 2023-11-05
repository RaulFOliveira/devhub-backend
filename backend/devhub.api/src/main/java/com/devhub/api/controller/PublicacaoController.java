package com.devhub.api.controller;

import com.devhub.api.ListaObj;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.DetailPublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import com.devhub.api.service.PublicacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {
    ListaObj<Publicacao> listaPublicacao = new ListaObj<>(100);

    @Autowired
    private PublicacaoService service;
    @Autowired
    private PublicacaoRepository publicacaoRepository;
    @Autowired
    private EspecialidadeDesejadaRepository especialidadeDesejadaRepository;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity criarPublicacao(@RequestBody @Valid CreatePublicacaoDTO data, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        var publicacao = service.realizarPublicacao(data, id);
        var uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(publicacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailPublicacaoDTO(publicacao));
    }

    @GetMapping
    public ResponseEntity<ListaObj<Publicacao>> mostrarPublicacoes() {
        var publicacoes = publicacaoRepository.findAll();

        if (publicacoes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        for (Publicacao publicacao: publicacoes) {
            listaPublicacao.adiciona(publicacao);
        }

        for (int i = 0; i < listaPublicacao.getTamanho() - 1; i++) {
            for (int j = 1; j < listaPublicacao.getTamanho()  - i; j++) {
                if (listaPublicacao.getElemento(j).getCreatedAt().isBefore(listaPublicacao.getElemento(j-1).getCreatedAt())) {
                    Publicacao aux = listaPublicacao.getElemento(j);
                    listaPublicacao.substitui(j, listaPublicacao.getElemento(j-1));
                    listaPublicacao.substitui(j-1, aux);
                }
            }
        }
        return ResponseEntity.status(200).body(listaPublicacao);
    }
}
