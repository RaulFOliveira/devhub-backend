package com.devhub.api.service;

import com.devhub.api.domain.especialidade.EspecialidadesEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EspecialidadeService {

    public List<String> pesquisarEspecialidades(String termoPesquisa) {
        var especialidades = new ArrayList<>(Arrays.stream(EspecialidadesEnum.values())
                .map(Enum::name)
                .filter(palavra -> palavra.toLowerCase().startsWith(termoPesquisa.toLowerCase()))
                .toList());

        Collections.sort(especialidades);
        List<String> especialidadesFormatadas = new ArrayList<>();
        for (String especialidade : especialidades) {
            var especialidadeFormatada = formatarEspecialidade(especialidade);
            especialidadesFormatadas.add(especialidadeFormatada);
        }
        return especialidadesFormatadas;
    }

    protected String formatarEspecialidade(String especialidade) {
        String especialidadeFormatada;
        if (especialidade.equals("JAVASCRIPT")) {
            return "JavaScript";
        } else if (especialidade.equals("C_SHARP")) {
            return "C#";
        } else if (especialidade.equals("TYPESCRIPT")) {
            return "TypeScript";
        } else if (especialidade.equals("OBJECTIVE_C")) {
            return "Objective-C";
        } else if (especialidade.equals("SHELL_SCRIPT")) {
            return "Shell Script";
        } else if (especialidade.equals("MATLAB")) {
            return especialidade;
        } else if (especialidade.equals("C_PLUS_PLUS")) {
            return "C++";
        } else if (especialidade.equals("DOT_NET")) {
            return ".NET";
        } else if (especialidade.endsWith("_JS")) {
            especialidade = especialidade.replace("_JS", ".js");
        } else if (especialidade.equals("SPRINGBOOT")) {
            return "SpringBoot";
        } else if (especialidade.equals("RUBY_ON_RAILS")) {
            return "Ruby on Rails";
        } else if (especialidade.equals("IOS")) {
            return "iOS";
        } else if (especialidade.equals("AWS")) {
            return especialidade;
        } else if (especialidade.equals("SQL")) {
            return especialidade;
        } else if (especialidade.equals("GOOGLE CLOUD")) {
            return "Google Cloud";
        } else if (especialidade.equals("CI_CD")) {
            especialidade = especialidade.replace("_", "/");
            return especialidade;
        } else if (especialidade.equals("BANCO_DE_DADOS")) {
            return "Banco de Dados";
        } else if (especialidade.equals("ARQUITETURA_DE_SOFTWARE")) {
            return "Arquitetura de Software";
        } else if (especialidade.equals("DESIGN_PATTERNS")) {
            return "Design Patterns";
        } else if (especialidade.equals("METODOLOGIAS_AGILE")) {
            return "Metodologias Ágeis";
        } else if (especialidade.equals("AZURE_DEVOPS")) {
            return "Azure DevOps";
        }
        especialidadeFormatada =
                especialidade.substring(0, 1)
                + especialidade.substring(1)
            .toLowerCase();

        return especialidadeFormatada;
    }
}
