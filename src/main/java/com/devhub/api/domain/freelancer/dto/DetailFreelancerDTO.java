package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;

public record DetailFreelancerDTO(Long id_freelancer, String nome, String telefone, String email, Integer contratacoes,
                                  Funcao funcao, Double valorHora, String senioridade, String descricao, Boolean ativo) {
    public DetailFreelancerDTO(Freelancer freelancer) {
        this(freelancer.getId(),
                freelancer.getNome(),
                freelancer.getTelefone(),
                freelancer.getEmail(),
                freelancer.getContratacoes(),
                freelancer.getFuncao(),
                freelancer.getValorHora(),
                freelancer.getSenioridade(),
                freelancer.getDescricao(),
                freelancer.getAtivo());
    }
}
