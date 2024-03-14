package com.devhub.api.controller;

import com.devhub.api.service.ConversaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversas")
public class ConversaController {

    @Autowired
    private ConversaService service;

    @PostMapping("/{idFreelancer}/{idContratante}")
    public ResponseEntity<Void> criarConversa(
            @PathVariable Long idFreelancer,
            @PathVariable Long idContratante
    ) {
        service.criarConversa(idFreelancer, idContratante);
        return ResponseEntity.status(201).build();
    }
}
