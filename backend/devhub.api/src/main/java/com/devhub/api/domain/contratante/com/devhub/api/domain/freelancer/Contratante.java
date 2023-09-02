package com.devhub.api.domain.freelancer;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;

@Table(name="contratantes")
@Entity(name="Contratante")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contratante {

    @id @GeneratevALUE(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private String cnpk;

    private String telefone;

    private String email;

    private Integer contratacoes;


    public Contratante(Long id, String nome, String cnpk, String telefone, String email, Integer contratacoes) {
        this.id = id;
        this.nome = nome;
        this.cnpk = cnpk;
        this.telefone = telefone;
        this.email = email;
        this.contratacoes = contratacoes;
    }

    public void atuallizarInformacoes(UpdateContratanteData data){
        if(data.nome() != null){
            this.nome = data.nome();
        }
        if (data.telefone() != null){
            this.telefone = data.telefone();
        }
        if(data.senha() != null){
            this.senha = data.senha();
        }

    }

}
