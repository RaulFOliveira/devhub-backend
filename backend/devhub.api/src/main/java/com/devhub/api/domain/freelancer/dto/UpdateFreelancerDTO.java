package com.devhub.api.domain.freelancer.dto;

public record UpdateFreelancerDTO(String nome, String telefone, String senha,
                                  String descricao, Double valorHora) {

}
