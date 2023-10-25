package com.devhub.api.domain.contratante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContratanteRepository extends JpaRepository<Contratante, Long> {
    Page<Contratante> findAllByAtivoTrue(Pageable paginacao);
    UserDetails findByEmail(String email);

    Contratante findByCnpj(String cnpj);
}
