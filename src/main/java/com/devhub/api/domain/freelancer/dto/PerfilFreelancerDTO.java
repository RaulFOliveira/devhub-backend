package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;

import java.util.List;

public record PerfilFreelancerDTO(Long id, String nome, String email, Funcao funcao,
                                  List<Especialidade> especialidades, Double valorHora,
                                  String senioridade, String descricao, String telefone,
                                  byte[] imagem, Double nota) {

}
