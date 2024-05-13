package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.usuario.UserRole;

public record DetailFreelancerDTO(Long id, String nome, String telefone, String email, Integer contratacoes,
                                  Funcao funcao, Double valorHora, String senioridade, String descricao, UserRole role,
                                  Boolean ativo) {
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
                freelancer.getRole(),
                freelancer.getAtivo());
    }
}
