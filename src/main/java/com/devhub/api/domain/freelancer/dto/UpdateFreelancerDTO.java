package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.funcao.Funcao;

public record UpdateFreelancerDTO(String nome, String telefone, String senha, Funcao funcao,
                                  String descricao, Double valorHora) {

}
