package com.devhub.api.domain.mensagem;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.conversa.Conversa;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagem")
@Getter
@Setter
@NoArgsConstructor
public class Mensagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Usuario remetente;
    private Usuario destinatario;
    private String conteudo;
    private LocalDateTime sendAt;

    @ManyToOne
    private Conversa conversa;

}
