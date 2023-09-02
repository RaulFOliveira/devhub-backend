package com.devhub.api.domain.freelancer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ContratanteRepository extends JpaRepository <Contratante, Long>{
    Page<Contratante> findAllByAtivoTrue(Pageable paginacao);
}
