package com.devhub.api.controller;

import com.devhub.api.domain.avaliacao_freelancer.dto.CreateAvaliacaoDTO;
import com.devhub.api.service.AvaliacaoFreelancerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/avaliacoes-freelancer")
public class AvaliacaoFreelancerController {

    @Autowired
    private AvaliacaoFreelancerService service;

    @PostMapping("/{idContratante}/{idFreelancer}")
    public ResponseEntity avaliarFreelancer(@PathVariable Long idContratante,
                                         @PathVariable Long idFreelancer,
                                         @RequestBody @Valid CreateAvaliacaoDTO data,
                                         UriComponentsBuilder uriBuilder) {
        var avaliacao = service.avaliarFreelancer(idContratante, idFreelancer, data);
        var uri = uriBuilder.path("/avaliacoes-freelancer/{id}").buildAndExpand(avaliacao.getId()).toUri();
        return ResponseEntity.created(uri).body(avaliacao);
    }

}
