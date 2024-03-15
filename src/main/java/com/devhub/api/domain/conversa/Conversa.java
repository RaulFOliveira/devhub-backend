package com.devhub.api.domain.conversa;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conversa")
@Getter
@Setter
@NoArgsConstructor
public class Conversa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_freelancer")
    private Freelancer freelancer;

    @ManyToOne
    @JoinColumn(name = "fk_contratante")
    private Contratante contratante;

    public Conversa(Freelancer freelancer, Contratante contratante) {
        this.freelancer = freelancer;
        this.contratante = contratante;
    }
}
