package com.devhub.api.service;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.conversa.Conversa;
import com.devhub.api.domain.conversa.ConversaRepository;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConversaService {

    @Autowired
    private ConversaRepository repo;
    @Autowired
    private FreelancerRepository repoFreelancer;
    @Autowired
    private ContratanteRepository repoContratante;

    public void criarConversa(Long idFreelancer, Long idContratante) {
        var contratante = repoContratante.findById(idContratante);
        var freelancer = repoFreelancer.findById(idFreelancer);
        if (contratante.isEmpty() || freelancer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ID do contratante ou do freelancer não foi encontrado");
        }

        var conversaJaExistente = repo.buscarConversaExistente(
                contratante.get(), freelancer.get()
        );
        if (conversaJaExistente.size() == 1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        repo.save(new Conversa(freelancer.get(), contratante.get()));
    }
}