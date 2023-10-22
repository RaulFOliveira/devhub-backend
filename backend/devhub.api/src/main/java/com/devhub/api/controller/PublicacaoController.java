package com.devhub.api.controller;

import com.devhub.api.ListaObj;
import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaData;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.CreatePublicacaoData;
import com.devhub.api.domain.publicacao.DetailPublicacaoData;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {
    ListaObj<Publicacao> listaPublicacao = new ListaObj<>(100);


    @Autowired
    private PublicacaoRepository publicacaoRepository;
    @Autowired
    private EspecialidadeDesejadaRepository especialidadeDesejadaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity criarPublicacao(@RequestBody @Valid CreatePublicacaoData data, UriComponentsBuilder uriBuilder) {
        var publicacao = new Publicacao(data);

        publicacaoRepository.save(publicacao);

        var listaEspecialidades = data.especialidadesDesejadas();
        for (EspecialidadeDesejadaData dataEspec : listaEspecialidades) {
            var especialidade = new EspecialidadeDesejada(dataEspec, publicacao);
            especialidadeDesejadaRepository.save(especialidade);
        }
        var uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(publicacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailPublicacaoData(publicacao));
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
