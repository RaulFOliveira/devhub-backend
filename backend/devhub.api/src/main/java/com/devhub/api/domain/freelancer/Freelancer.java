package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.funcao.Funcao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "freelancers")
@Entity(name = "Freelancer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_freelancer")
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_freelancer;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Integer contratacoes;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    private Double valorHora;

    @JsonManagedReference
    @OneToMany(mappedBy = "freelancer")
    private List<Especialidade> especialidades;

    private String descricao;
    private Boolean ativo;


    public Freelancer(CreateFreelancerData data) {
        this.ativo = true;
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.telefone = data.telefone();
        this.email = data.email();
        this.contratacoes = 0;
        this.funcao = data.funcao();
        this.valorHora = data.valorHora();
        this.descricao = data.descricao();

    }

    public void atuallizarInformacoes(UpdateFreelancerData data) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }
        if (data.telefone() != null) {
            this.telefone = data.telefone();
        }
//        if(data.senha() != null){
//            this.senha = data.senha();
//        }
        if (data.descricao() != null) {
            this.descricao = data.descricao();
        }
        if (data.valorHora() != null) {
            this.valorHora = data.valorHora();
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public void ativarConta() {
        this.ativo = true;
    }
}
