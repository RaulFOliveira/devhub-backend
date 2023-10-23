package com.devhub.api.controller;

import com.devhub.api.domain.contratante.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/contratantes", produces = {"application/json"})
@Tag(name = "Api DevHub")
public class ContratanteController {

    @Autowired
    private ContratanteRepository repository;

    @Transactional
    @Operation(summary = "Realiza a criaçao do Contratante", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criaçao de um novo contratante"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createContratante(@Valid @RequestBody CreateContratanteData data, UriComponentsBuilder uriBuilder) {

        var contratante = new Contratante(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        contratante.setSenha(encryptedPassword);

        repository.save(contratante);

        var uri = uriBuilder.path("/contratantes/{id}").buildAndExpand(contratante.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailContratanteData(contratante));
    }

    @Operation(summary = "Realiza a listagenm dos Contratantes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante listados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem dos contratantes"),
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ListContratanteData>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListContratanteData::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @Operation(summary = "Realiza a atualizaçao de um dos Contratantes", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualizaçao do contratante"),
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atualizar(@Valid @RequestBody UpdateContratanteData data) {
        var contratante = repository.getReferenceById(data.id());
        contratante.atuallizarInformacoes(data);

        return ResponseEntity.ok(new DetailContratanteData(contratante));
    }

    @Transactional
    @Operation(summary = "Realiza a exclusao de um contratante", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante excluido com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a exclusao de um contratante"),
    })
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity excluir(@PathVariable Long id) {
        var contratante = repository.getReferenceById(id);
        contratante.excluir();
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Operation(summary = "Realiza a ativaçao de uma conta de Contratante", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a ativaçao da conta"),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ativarConta(@PathVariable Long id) {
        var contratante = repository.getReferenceById(id);
        contratante.ativarConta();
        return ResponseEntity.noContent().build();
    }
}
