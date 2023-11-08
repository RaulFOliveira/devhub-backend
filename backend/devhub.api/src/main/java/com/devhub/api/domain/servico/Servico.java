package com.devhub.api.domain.servico;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Table(name = "servico")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer horasTrabalhadas;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "fk_freelancer")
    private Freelancer freelancer;

    @ManyToOne
    @JoinColumn(name = "fk_contratante")
    private Contratante contratante;

    public Servico(CreateServicoDTO data, Contratante contratante, Freelancer freelancer) {
        this.horasTrabalhadas = data.horasTrabalhadas();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = LocalDate.now().format(dateTimeFormatter);
        this.createdAt = LocalDate.parse(dataString, dateTimeFormatter);
        this.freelancer = freelancer;
        this.contratante = contratante;
    }
}
