package com.devhub.api.domain.contratante;
import jakarta.persistence.*;
import lombok.*;

@Table(name="contratantes")
@Entity(name="Contratante")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contratante {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private String cnpj;

    private String telefone;

    private String email;

    private Integer contratacoes;

    private Boolean ativo;


    public Contratante(CreateContratanteData data) {
        this.ativo = true;
        this.nome = data.nome();
        this.cnpj = data.cnpj();
        this.telefone = data.telefone();
        this.email = data.email();
        this.contratacoes = 0;
    }

    public void atuallizarInformacoes(UpdateContratanteData data){
        if(data.nome() != null){
            this.nome = data.nome();
        }
        if (data.telefone() != null){
            this.telefone = data.telefone();
        }
//        if(data.senha() != null){
//            this.senha = data.senha();
//        }

    }

    public void excluir(){
        this.ativo = false;
    }

    public void ativarConta(){
        this.ativo = true;
    }

}
