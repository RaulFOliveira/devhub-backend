package com.devhub.api.service;

import com.devhub.api.domain.avaliacao_freelancer.AvaliacaoFreelancer;
import com.devhub.api.domain.avaliacao_freelancer.AvaliacaoFreelancerRepository;
import com.devhub.api.domain.avaliacao_freelancer.dto.CreateAvaliacaoDTO;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliacaoFreelancerService {

    @Autowired
    private AvaliacaoFreelancerRepository repo;
    @Autowired
    private FreelancerRepository freelancerRepo;
    @Autowired
    private ContratanteRepository contratanteRepo;

    public AvaliacaoFreelancer avaliarUsuario(Long idContratante, Long idFreelancer, CreateAvaliacaoDTO data) {
        var contratante = contratanteRepo.findById(idContratante);
        var freelancer = freelancerRepo.findById(idFreelancer);

        if (contratante.isEmpty() || freelancer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ID do avaliador ou do avaliado não existe!");
        }

        return repo.save(new AvaliacaoFreelancer(
            contratante.get(), freelancer.get(), data.nota()
        ));
    }
}
