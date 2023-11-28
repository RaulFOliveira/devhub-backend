package com.devhub.api.domain.conversa;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    @Query("""
    select c from Conversa c
    where c.contratante = ?1 and c.freelancer = ?2
    """)
    List<Conversa> buscarConversaExistente(Contratante contratante, Freelancer freelancer);
}