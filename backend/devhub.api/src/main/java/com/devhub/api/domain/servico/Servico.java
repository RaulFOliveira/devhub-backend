package com.devhub.api.domain.servico;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private int duracao;
    private String horastrabalhadas;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fk_freelancer")
    private Freelancer freelancer;

    @ManyToOne
    @JoinColumn(name = "fk_contratante")
    private Contratante contratante;

    public Servico(CreateServicoData data, Contratante contratante, Freelancer freelancer) {
        this.duracao = data.duracao();
        this.horastrabalhadas = data.horasTrabalhadas();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataString = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(dateTimeFormatter);
        this.createdAt = LocalDateTime.parse(dataString, dateTimeFormatter);
    }
}
