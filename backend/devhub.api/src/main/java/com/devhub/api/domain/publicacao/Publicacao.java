package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.usuario.UserRole;
import com.devhub.api.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

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
    private String role;
    private LocalDateTime createdAt;
    private Long id_usuario;
    public Publicacao(CreatePublicacaoDTO data) {
        this.titulo = data.titulo();
        this.descricao = data.descricao();
        this.role = data.role();
        this.id_usuario = data.id_usuario();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataString = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(dateTimeFormatter);
        this.createdAt = LocalDateTime.parse(dataString, dateTimeFormatter);

    }
}
