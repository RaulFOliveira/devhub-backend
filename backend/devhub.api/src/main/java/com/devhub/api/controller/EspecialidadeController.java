package com.devhub.api.controller;

import com.devhub.api.domain.especialidade.EspecialidadesEnum;
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

    @GetMapping("/filtrar")
    public ResponseEntity<List<String>> filtrarPalavras(@RequestParam String termoPesquisa) {
        var especialidades = Arrays.stream(EspecialidadesEnum.values())
                .map(Enum::name)
                .filter(palavra -> palavra.toLowerCase().startsWith(termoPesquisa.toLowerCase()))
                .toList();

        especialidades.stream()
                .map(especialidade ->
                        especialidade
                                .toLowerCase()
                                .substring(0,1)
                                .toUpperCase()
                );
        return ResponseEntity.status(201).body(especialidades);
    }
}