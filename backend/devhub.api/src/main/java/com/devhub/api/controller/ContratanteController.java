package com.devhub.api.controller;

import com.devhub.api.domain.contratante.*;
import com.devhub.api.domain.contratante.dto.CreateContratanteDTO;
import com.devhub.api.domain.contratante.dto.DetailContratanteDTO;
import com.devhub.api.domain.contratante.dto.ListContratanteDTO;
import com.devhub.api.domain.contratante.dto.UpdateContratanteDTO;
import com.devhub.api.service.ContratanteService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping(value = "/contratantes", produces = {"application/json"})
@Tag(name = "Api DevHub")
public class ContratanteController {

    @Autowired
    private ContratanteRepository repository;
    @Autowired
    private ContratanteService service;

    @Operation(summary = "Realiza a criação do Contratante", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criaçao de um novo contratante"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createContratante(@Valid @RequestBody CreateContratanteDTO data, UriComponentsBuilder uriBuilder) {
        var contratante = service.cadastrarContratante(data);
        var uri = uriBuilder.path("/contratantes/{id}").buildAndExpand(contratante.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailContratanteDTO(contratante));
    }

    @Operation(summary = "Realiza a listagenm dos Contratantes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante listados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem dos contratantes"),
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ListContratanteDTO>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = service.getContratantes(paginacao);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Realiza a atualizaçao de um dos Contratantes", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualizaçao do contratante"),
    })
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailContratanteDTO> atualizar(@Valid @RequestBody UpdateContratanteDTO data, @PathVariable Long id) {
        var contratante = service.atualizar(data, id);
        return ResponseEntity.ok(new DetailContratanteDTO(contratante));
    }

    @Operation(summary = "Realiza a exclusao de um contratante", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contratante excluido com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a exclusao de um contratante"),
    })
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Operation(summary = "Realiza a ativaçao de uma conta de Contratante", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a ativação da conta"),
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ativarConta(@PathVariable Long id) {
        var contratante = repository.getReferenceById(id);
        contratante.ativarConta();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/foto/{codigo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> patchFoto(@PathVariable int codigo,
                                          @RequestPart("image") MultipartFile novaFoto) throws IOException {
        return ResponseEntity.status(service.atualizarFoto(novaFoto, codigo)).build();
    }
}
