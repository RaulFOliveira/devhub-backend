package com.devhub.api.domain.freelancer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Page<Freelancer> findAllByAtivoTrue(Pageable paginacao);

    UserDetails findByEmail(String email);
}
