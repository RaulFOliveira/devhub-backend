package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Table(name = "publicacao")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Publicacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @ManyToOne
    private Contratante contratante;
    private LocalDate createdAt;
    @OneToMany
    private List<EspecialidadeDesejada> especialidadesDesejadas;

    public Publicacao(CreatePublicacaoData data) {
        this.titulo = data.titulo();
        this.descricao = data.descricao();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        this.createdAt = LocalDate.parse(LocalDate.now().format(dateTimeFormatter));

//        this.contratante = contratante;
    }
}
