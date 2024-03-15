package com.devhub.api.domain.avaliacao_freelancer;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.usuario.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "avaliacao_freelancer")
@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoFreelancer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_avaliador")
    private Contratante fkAvaliador;

    @ManyToOne
    @JoinColumn(name = "fk_avaliado")
    private Freelancer fkAvaliado;

    private Double nota;

    public AvaliacaoFreelancer(Contratante fkAvaliador, Freelancer fkAvaliado, double nota) {
        this.fkAvaliador = fkAvaliador;
        this.fkAvaliado = fkAvaliado;
        this.nota = nota;
    }
}
