package com.devhub.api.domain.servico;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(s.status) > 0
            THEN
                true
            ELSE
                false
            END
            FROM
                Servico s
            WHERE
                s.freelancer = ?1
            AND
                s.contratante = ?2
            AND
                s.status = 'Em andamento'
            ORDER BY
                s.createdAt
            DESC
            """)
    boolean existsByEstadoEmAndamento(Freelancer freelancer, Contratante contratante);

    @Query("""
    SELECT
        s
    FROM
        Servico s
    WHERE
        s.freelancer = ?1
    AND
        s.contratante = ?2
    AND
        s.status = 'Em andamento'
    ORDER BY
        s.createdAt
    DESC
    """)
    Servico getByUsers(Freelancer freelancer, Contratante contratante);
}
