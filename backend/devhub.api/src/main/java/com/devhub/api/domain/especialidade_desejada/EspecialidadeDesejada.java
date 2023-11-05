package com.devhub.api.domain.especialidade_desejada;

import com.devhub.api.domain.publicacao.Publicacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "especialidade_desejada")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class EspecialidadeDesejada {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeEspecialidade;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_publicacao")
    private Publicacao publicacao;

    public EspecialidadeDesejada(EspecialidadeDesejadaDTO data, Publicacao publicacao) {
        this.nomeEspecialidade = data.nome();
        this.publicacao = publicacao;
    }
}
