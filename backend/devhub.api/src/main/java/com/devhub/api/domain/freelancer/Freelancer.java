package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.especialidades.Especialidades;
import com.devhub.api.domain.especialidades.EspecialidadesData;
import com.devhub.api.domain.freelancer.CreateFreelancerData;
import com.devhub.api.domain.funcao.Funcao;
import jakarta.persistence.*;
import lombok.*;

@Table(name="freelancers")
@Entity(name="Freelancer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Freelancer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Integer contratacoes;
    @Enumerated(EnumType.STRING)
    private Funcao funcao;
    private Double valorHora;

//    @Embedded
//    private Especialidades especialidades;
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
        //       TODO: validar associativa entre tecnologia e freelancer
        //        this.especialidades = new Especialidades(data.especialidades());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getContratacoes() {
        return contratacoes;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
