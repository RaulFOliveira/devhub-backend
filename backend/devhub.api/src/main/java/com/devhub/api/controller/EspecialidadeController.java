package com.devhub.api.controller;

import com.devhub.api.domain.especialidade.EspecialidadesEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    @GetMapping
    public ResponseEntity<List<String>> getEspecialidades() {
        var especialidades = Arrays.stream(EspecialidadesEnum.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.status(200).body(especialidades);
    }
}
