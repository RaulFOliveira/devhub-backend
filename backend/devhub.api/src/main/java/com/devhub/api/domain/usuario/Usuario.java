package com.devhub.api.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter
@Setter
@MappedSuperclass
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

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 10 * 1024 * 1024)
    private byte[] imagem;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Usuario() {
    }

    public Usuario(String nome, String telefone, String email, String senha, UserRole role) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.contratacoes = 0;
        this.ativo = true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.CONTRATANTE) return List.of(new SimpleGrantedAuthority("ROLE_CONTRATANTE"));
        else return List.of(new SimpleGrantedAuthority("ROLE_FREELANCER"));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}