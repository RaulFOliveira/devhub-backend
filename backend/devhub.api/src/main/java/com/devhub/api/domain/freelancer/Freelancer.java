package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.freelancer.dto.CreateFreelancerDTO;
import com.devhub.api.domain.freelancer.dto.UpdateFreelancerDTO;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.usuario.UserRole;
import com.devhub.api.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "freelancer")
@Entity()
@Getter
@Setter
public class Freelancer extends Usuario {

    private String cpf;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    private Double valorHora;

    @JsonManagedReference
    @OneToMany(mappedBy = "freelancer")
    private List<Especialidade> especialidades;

    private String descricao;

    private String senioridade;

//    @JsonIgnore
//    @Column(length = 10 * 1024 * 1024) // definindo o tamanho em 10MB
//    private byte[] foto;

//    @JsonIgnore
//    @Column(length = 10 * 1024 * 1024)
//    private byte[] imagem;

    public Freelancer() {
        super();
    }

    public Freelancer(CreateFreelancerDTO data) {
        super(data.nome(), data.telefone(),data.email(), data.senha(), UserRole.FREELANCER);
        this.cpf = data.cpf();
        this.funcao = data.funcao();
        this.valorHora = data.valorHora();
        this.descricao = data.descricao();
        this.senioridade = data.senioridade();
//        this.imagem = null;
    }

    public void atuallizarInformacoes(UpdateFreelancerDTO data) {
        if (data.nome() != null) {
            this.nome = data.nome();
        }
        if (data.telefone() != null) {
            this.telefone = data.telefone();
        }
        if(data.senha() != null){
            this.senha = data.senha();
        }
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

    public void ativarConta() { this.ativo = true; }
}
