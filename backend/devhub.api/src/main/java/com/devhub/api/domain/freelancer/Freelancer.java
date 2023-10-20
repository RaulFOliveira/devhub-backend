package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.usuario.UserRole;
import com.devhub.api.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "freelancer")
@Entity()
@Getter
@Setter
public class Freelancer extends Usuario {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    private Double valorHora;

    @JsonManagedReference
    @OneToMany(mappedBy = "freelancer")
    private List<Especialidade> especialidades;

    private String descricao;

    @OneToOne
    private Usuario fkUsuario;


    public Freelancer(CreateFreelancerData data, Usuario fkUsuario) {
        super(data.nome(), data.telefone(), data.email(), data.senha(), UserRole.USER);
        this.cpf = data.cpf();
        this.funcao = data.funcao();
        this.valorHora = data.valorHora();
        this.descricao = data.descricao();
        this.fkUsuario = fkUsuario;
    }

    public void atuallizarInformacoes(UpdateFreelancerData data) {
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
