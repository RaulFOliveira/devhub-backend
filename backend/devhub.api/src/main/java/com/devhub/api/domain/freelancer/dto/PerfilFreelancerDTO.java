package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;

import java.util.List;

public record PerfilFreelancerDTO(Long id_freelancer, String nome, String telefone, String email, Integer contratacoes,
                                  Funcao funcao, List<Especialidade> especialidades, Double valorHora, String senioridade, String descricao,
                                  byte[] imagem , Boolean ativo) {
    public PerfilFreelancerDTO(Freelancer freelancer) {
        this(freelancer.getId(), freelancer.getNome(), freelancer.getTelefone(),
            freelancer.getEmail(), freelancer.getContratacoes(), freelancer.getFuncao(),
            freelancer.getEspecialidades(), freelancer.getValorHora(), freelancer.getSenioridade(),
            freelancer.getDescricao(), freelancer.getImagem(), freelancer.getAtivo()
            );
    }
}
