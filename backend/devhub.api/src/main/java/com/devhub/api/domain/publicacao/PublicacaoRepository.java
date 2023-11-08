package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    @Query("""
    select p from Publicacao p where p.contratante = ?1 order by createdAt desc        
    """)
    List<Publicacao> findByContratante(Contratante contratante);

    List<Publicacao> findAllByOrderByCreatedAtDesc();
}
