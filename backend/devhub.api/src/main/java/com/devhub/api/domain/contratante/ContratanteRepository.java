package com.devhub.api.domain.contratante;

import com.devhub.api.domain.contratante.dto.ContratanteValidacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ContratanteRepository extends JpaRepository<Contratante, Long> {
    Page<Contratante> findAllByAtivoTrue(Pageable paginacao);
    UserDetails findByEmail(String email);

    Contratante findByCnpj(String cnpj);

    @Query("""
    select new com.devhub.api.domain.contratante.dto.ContratanteValidacaoDTO(c.email, c.cnpj, c.telefone)
    from Contratante c
    """)
    List<ContratanteValidacaoDTO> validarDadosUnicos();
}
