package com.devhub.api.domain.avaliacao_freelancer;

import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.usuario.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvaliacaoFreelancerRepository extends JpaRepository<AvaliacaoFreelancer, Long> {
    @Query("""
    select avg(a.nota) from AvaliacaoFreelancer a
    where a.fkAvaliado = ?1
    """)
    Double somarTodasAsNotas(Freelancer freelancer);

}
