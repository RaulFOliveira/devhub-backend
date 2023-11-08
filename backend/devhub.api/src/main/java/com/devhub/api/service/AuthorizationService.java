package com.devhub.api.service;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.usuario.AutenticacaoData;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.infra.security.TokenJWTData;
import com.devhub.api.infra.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private FreelancerRepository freelancerRepository;
    @Autowired
    private ContratanteRepository contratanteRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            if (freelancerRepository.findByEmail(username) != null) {
                return freelancerRepository.findByEmail(username);
            } else if (contratanteRepository.findByEmail(username) != null) {
                return contratanteRepository.findByEmail(username);
            } else {
                throw new UsernameNotFoundException("E-mail e/ou senha inválidos");
            }
        } catch (Exception e){
            return null;
        }
    }
}
