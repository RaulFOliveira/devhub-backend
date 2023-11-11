package com.devhub.api.domain.especialidade_desejada;

import com.devhub.api.domain.publicacao.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EspecialidadeDesejadaRepository extends JpaRepository<EspecialidadeDesejada, Long> {

    @Modifying
    @Query("""
    DELETE FROM EspecialidadeDesejada e WHERE e.publicacao = ?1         
    """)
    void deleteAllByFkPublicacao(Publicacao publicacao);
}
