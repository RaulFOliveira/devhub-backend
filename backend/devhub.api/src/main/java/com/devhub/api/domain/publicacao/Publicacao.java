package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_contratante")
    private Contratante contratante;
    private LocalDateTime createdAt;
    @JsonManagedReference
    @OneToMany(mappedBy = "publicacao")
    private List<EspecialidadeDesejada> especialidadesDesejadas;

    public Publicacao(CreatePublicacaoDTO data, Contratante contratante) {
        this.titulo = data.titulo();
        this.descricao = data.descricao();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataString = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(dateTimeFormatter);
        this.createdAt = LocalDateTime.parse(dataString, dateTimeFormatter);
        this.contratante = contratante;
    }
}
