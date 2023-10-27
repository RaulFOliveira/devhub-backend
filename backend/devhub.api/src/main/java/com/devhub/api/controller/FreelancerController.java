package com.devhub.api.controller;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.especialidade.EspecialidadeData;
import com.devhub.api.domain.especialidade.EspecialidadeRepository;
import com.devhub.api.domain.freelancer.*;
import com.devhub.api.service.FreelancerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/freelancers", produces = {"application/json"})
@Tag(name = "Api DevHub")
public class FreelancerController {

    @Autowired
    private FreelancerService service;
    @Autowired
    private FreelancerRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;


    @Operation(summary = "Realiza a criaçâo do freelancer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a validação do token"),
    })

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createFreelancer(@Valid @RequestBody CreateFreelancerData data, UriComponentsBuilder uriBuilder) {
        try {
            var response = service.cadastrarFreelancer(data, uriBuilder);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza a listagenm dos Freelancers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancers listados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem dos Freelancers"),
    })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ListFreelancerData>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = service.getFreelancers(paginacao);
        if (!page.hasContent()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Realiza a listagenm de um Freelancer especifico por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer listado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem do freelancer respectivo"),
    })

    @GetMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListFreelancerData> listarFreelancerById(@PathVariable Long id) {
        var freelancer = service.getFreelancerById(id);
        return freelancer != null ?
                ResponseEntity.ok(new ListFreelancerData(freelancer))
                : ResponseEntity.notFound().build();
    }

    @Transactional
    @Operation(summary = "Realiza a atualização de um dos freelancers", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualizaçao do freelancer"),
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atualizarFreelancer(@Valid @RequestBody UpdateFreelancerData data, @PathVariable Long id) {
        var freelancer = service.atualizar(data, id);

        return freelancer != null ?
                ResponseEntity.ok(new DetailFreelancerData(freelancer))
                : ResponseEntity.notFound().build();
    }

    @Transactional
    @Operation(summary = "Realiza a exclusao de um freelancer", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer excluido com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a exclusão de um Freelancer"),
    })
    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity excluir(@PathVariable Long id) {
        var freelancer = service.excluir(id);
        if (freelancer == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @Operation(summary = "Realiza a ativaçao de uma conta de Freelancer", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a ativaçao da conta"),
    })

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity ativarConta(@PathVariable Long id) {
        var freelancer = repository.getReferenceById(id);
        freelancer.ativarConta();
        return ResponseEntity.noContent().build();
    }
}
