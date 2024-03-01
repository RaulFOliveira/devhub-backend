package com.devhub.api.controller;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoAgendadaDTO;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.dto.DetailPublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.usuario.UserRole;
import com.devhub.api.service.PublicacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//    @PostMapping("/agendar/{id}")
//    public ResponseEntity agendarPublicacoes(
//            @RequestBody @Valid List<CreatePublicacaoDTO> data, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
//        service.enfileirarPublicacoes(data, id);
//        return ResponseEntity.status(200).build();
//    }
//
//    @PostMapping("/agendar")
//    public ResponseEntity publicarAgendados(@RequestBody @Valid CreatePublicacaoAgendadaDTO data) {
//        var publicacoesAgendadas = service.realizarPublicacoesAgendadas(data.qtdPublicacoes());
//        return ResponseEntity.status(201).body(publicacoesAgendadas);
//    }
//
//    @PostMapping("/txt/{id}")
//    public ResponseEntity publicarEmLote(@RequestParam("file") MultipartFile arquivoTxt, @PathVariable Long id) throws IOException {
//        service.publicarEmLote(arquivoTxt, id);
//        return ResponseEntity.status(201).build();
//    }

    @GetMapping
    public ResponseEntity<List<ListaPublicacaoDTO>> mostrarPublicacoes() {
        var publicacoes = service.mostrarPublicacoes();
        return ResponseEntity.status(200).body(publicacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Publicacao>> mostrarPublicacoesById(@PathVariable Long id) {
        var publicacoes = service.mostrarPublicacoesByid(id);
        return ResponseEntity.status(200).body(publicacoes);
    }

    @DeleteMapping("/{idPublicacao}")
    public ResponseEntity deletarPublicacao(@PathVariable Long idPublicacao) {
        service.deletarPublicacao(idPublicacao);
        return ResponseEntity.status(200).build();
    }
}
