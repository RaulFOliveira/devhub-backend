package com.devhub.api.domain.especialidade_desejada;

import com.devhub.api.domain.publicacao.Publicacao;
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
    @ManyToOne
    private Publicacao publicacao;

    public EspecialidadeDesejada(EspecialidadeDesejadaData data) {
        this.nomeEspecialidade = data.nome();
    }
}
