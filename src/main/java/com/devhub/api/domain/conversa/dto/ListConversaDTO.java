package com.devhub.api.domain.conversa.dto;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.conversa.Conversa;
import com.devhub.api.domain.freelancer.Freelancer;

public record ListConversaDTO(Long id, Freelancer freelancer, Contratante contratante) {
    public ListConversaDTO(Conversa conversa) {
        this(conversa.getId(), conversa.getFreelancer(), conversa.getContratante());
    }
}
