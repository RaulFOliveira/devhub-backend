package com.devhub.api.controller;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.especialidade.EspecialidadeData;
import com.devhub.api.domain.especialidade.EspecialidadeRepository;
import com.devhub.api.domain.freelancer.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping(value = "/freelancers", produces = {"application/json"})
@Tag(name = "Api DevHub")
public class FreelancerController {

    @Autowired
    private FreelancerRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Transactional
    @Operation(summary = "Realiza a criaçao do freelancer", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "o"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a validaçao do token"),
    })

    @PostMapping(consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity createFreelancer(@Valid @RequestBody CreateFreelancerData data, UriComponentsBuilder uriBuilder) {

        var freelancer = new Freelancer(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        freelancer.setSenha(encryptedPassword);

        repository.save(freelancer);

        var listaEspecialidades = data.especialidades();

        for (EspecialidadeData dataEspec : listaEspecialidades) {
            var especialidade = new Especialidade(dataEspec, freelancer);
            especialidadeRepository.save(especialidade);
        }

        var uri = uriBuilder.path("/freelancers/{id}").buildAndExpand(freelancer.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailFreelancerData(freelancer));
    }

    @Operation(summary = "Realiza a listagenm dos Freelancers", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancers listados com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem dos Freelancers"),
    })

    @GetMapping(consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity<Page<ListFreelancerData>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListFreelancerData::new);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Realiza a listagenm de um Freelancer especifico por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer listado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a listagem do freelancer respectivo"),
    })

    @GetMapping("/{id}",consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity<ListFreelancerData> listarFreelancerById(@PathVariable Long id) {
        var freelancer = repository.getReferenceById(id);
        return ResponseEntity.ok(new ListFreelancerData(freelancer));
    }

    @Transactional
    @Operation(summary = "Realiza a atualizaçao de um dos freelancers", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualizaçao do freelancer"),
    })

    @PutMapping(consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity atualizar(@Valid @RequestBody ("dados") UpdateFreelancerData data) {
        var freelancer = repository.getReferenceById(data.id_freelancer());
        freelancer.atuallizarInformacoes(data);

        return ResponseEntity.ok(new DetailFreelancerData(freelancer));
    }

    @Transactional
    @Operation(summary = "Realiza a exclusao de um freelancer", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer excluido com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a exclusao de um Freelancer"),
    })
    @DeleteMapping("/{id}",consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity excluir(@PathVariable Long id) {
        var freelancer = repository.getReferenceById(id);
        freelancer.excluir();
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

    @Patchapping("{/id}", consumes = MediaType.APPLICATION_JSON)

    public ResponseEntity ativarConta(@PathVariable Long id) {
        var freelancer = repository.getReferenceById(id);
        freelancer.ativarConta();
        return ResponseEntity.noContent().build();
    }
}
