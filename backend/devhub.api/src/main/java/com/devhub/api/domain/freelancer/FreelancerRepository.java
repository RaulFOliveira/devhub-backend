package com.devhub.api.domain.freelancer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Page<Freelancer> findAllByAtivoTrue(Pageable paginacao);

    UserDetails findByEmail(String email);

    Freelancer findByNomeAndTelefoneAndEmailAndValorHoraAndSenioridade(String nomeFreelancer, String telefone, String email, Double valorHora, String senioridade);

//    boolean existsByEmailOrCpfOrTelefone(String email, String cpf, String telefone);

    @Query("""
    select new com.devhub.api.domain.freelancer.FreelancerValidacaoDTO(f.email, f.cpf, f.telefone)
    from Freelancer f
    """)
    List<FreelancerValidacaoDTO> validarDadosUnicos();
}
