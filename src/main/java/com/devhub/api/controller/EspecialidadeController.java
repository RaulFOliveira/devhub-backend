package com.devhub.api.controller;

import com.devhub.api.domain.especialidade.EspecialidadesEnum;
import com.devhub.api.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService service;

    @GetMapping("/filtrar")
    public ResponseEntity<List<String>> filtrarPalavras(@RequestParam String termoPesquisa) {
        var especialidades = service.pesquisarEspecialidades(termoPesquisa);
        return ResponseEntity.status(200).body(especialidades);
    }
}