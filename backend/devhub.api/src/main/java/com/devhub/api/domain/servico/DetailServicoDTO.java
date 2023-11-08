package com.devhub.api.domain.servico;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;

import java.time.LocalDate;

public record DetailServicoDTO(Long id, Contratante contratante, Freelancer freelancer, Integer horasTrabalhadas, LocalDate createdAt ) {

    public DetailServicoDTO(Servico servico){
        this(servico.getId(), servico.getContratante(), servico.getFreelancer(), servico.getHorasTrabalhadas(), servico.getCreatedAt());
    }
}
