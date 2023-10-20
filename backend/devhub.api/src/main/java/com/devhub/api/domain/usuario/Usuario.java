package com.devhub.api.domain.usuario;

import com.devhub.api.domain.contratante.UpdateContratanteData;
import com.devhub.api.domain.freelancer.UpdateFreelancerData;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nome;

    protected String telefone;

    protected String email;

    protected String senha;

    protected int contratacoes;

    protected Boolean ativo;

    private UserRole role;

    public Usuario(String nome, String telefone, String email, String senha, UserRole role) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.contratacoes = 0;
        this.ativo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.USER) return List.of(new SimpleGrantedAuthority("ROLE_FREELANCER"), new SimpleGrantedAuthority("ROLE_CONTRATANTE"));
        else return List.of(new SimpleGrantedAuthority("ROLE_FREELANCER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}