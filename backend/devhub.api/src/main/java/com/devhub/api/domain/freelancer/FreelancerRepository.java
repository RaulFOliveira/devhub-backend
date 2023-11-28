package com.devhub.api.domain.freelancer;

import com.devhub.api.domain.freelancer.dto.FreelancerValidacaoDTO;
import com.devhub.api.domain.freelancer.dto.ListaFreelancerDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
//    Page<Freelancer> findAllByAtivoTrue(Pageable paginacao);
    List<Freelancer> findAll();

    UserDetails findByEmail(String email);

    Freelancer findByNomeAndTelefoneAndEmailAndValorHoraAndSenioridade(String nomeFreelancer, String telefone, String email, Double valorHora, String senioridade);

    @Query("""
    select new com.devhub.api.domain.freelancer.dto.FreelancerValidacaoDTO(f.email, f.cpf, f.telefone)
    from Freelancer f
    """)
    List<FreelancerValidacaoDTO> validarDadosUnicos();

    @Modifying
    @Transactional
    @Query("""
        update Freelancer f set f.imagem = ?1
        where f.id = ?2     
            """)
    int atualizarFoto(byte[] foto, int idFreelancer);

}
