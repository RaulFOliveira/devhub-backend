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
    private String estado;
    private Double valorPagamento;
    private LocalDate createdAt;
    private LocalDate finishedAt;

    @ManyToOne
    @JoinColumn(name = "fk_freelancer")
    private Freelancer freelancer;

    @ManyToOne
    @JoinColumn(name = "fk_contratante")
    private Contratante contratante;

    public Servico(Contratante contratante, Freelancer freelancer) {
        this.valorPagamento = null;
        this.estado = "Em andamento";
        this.finishedAt = null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = LocalDate.now().format(dateTimeFormatter);
        this.createdAt = LocalDate.parse(dataString, dateTimeFormatter);

        this.freelancer = freelancer;
        this.contratante = contratante;
    }
}
