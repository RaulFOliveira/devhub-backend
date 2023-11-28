package com.devhub.api.domain.freelancer.dto;

import com.devhub.api.domain.avaliacao_freelancer.AvaliacaoFreelancer;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.funcao.Funcao;
import lombok.Data;
import lombok.Getter;


public record ListaFreelancerDTO (
        Long id,
        String nome,
        byte[] imagem,
        Funcao funcao,
        String senioridade,
        Double valorHora,
        Double nota
){

}
