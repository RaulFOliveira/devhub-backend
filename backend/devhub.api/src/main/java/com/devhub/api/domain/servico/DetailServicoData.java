package com.devhub.api.domain.servico;

import java.time.LocalDateTime;

public record DetailServicoData(Long id, int duracao, String horasTrabalhadas, LocalDateTime createdAt ) {

    public DetailServicoData(Servico servico){
        this(servico.getId(), servico.getDuracao(), servico.getHorastrabalhadas(), servico.getCreatedAt());
    }
}
