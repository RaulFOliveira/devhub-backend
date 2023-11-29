package com.devhub.api.domain.especialidade;

import com.devhub.api.domain.freelancer.Freelancer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "especialidade")
@Entity()
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_especialidade")
public class Especialidade {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_especialidade;

    private String descricao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_freelancer")
    private Freelancer freelancer;

    public Especialidade(String descricao, Freelancer freelancer) {
        this.descricao = descricao;
        this.freelancer = freelancer;
    }
}
