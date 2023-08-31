package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.funcao.Funcao;

public record ListFreelancerData(Long id, String nome, String telefone, String email, Integer contratacoes, Funcao funcao, Double valorHora, String descricao, Boolean ativo) {
    public ListFreelancerData(Freelancer freelancer) {
        this(freelancer.getId(), freelancer.getNome(), freelancer.getTelefone(), freelancer.getEmail(), freelancer.getContratacoes(),
            freelancer.getFuncao(),freelancer.getValorHora(),freelancer.getDescricao(),freelancer.getAtivo());
    }
}
