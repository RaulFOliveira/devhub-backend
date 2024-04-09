package com.devhub.api.service;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.servico.FinishServicoDTO;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.servico.ServicoRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private ContratanteRepository contratanteRepository;

    static List<Servico> listaLida = new ArrayList<>();

    public Servico criarServico(Long idFreelancer, Long idContratante) {
        Freelancer freelancer = freelancerRepository.findById(idFreelancer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Freelancer não identificado"));
        Contratante contratante = contratanteRepository.findById(idContratante)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contratante não identificado"));

        if (verificarServicoEmAndamento(idContratante, idFreelancer)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe um serviço em andamento entre esses dois usuários");
        }

        var servico = new Servico(contratante, freelancer);
        return servicoRepository.save(servico);
    }

    public void concluirServico(FinishServicoDTO data) {
        Freelancer freelancer = freelancerRepository.findById(data.idFreelancer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Freelancer não identificado"));
        Contratante contratante = contratanteRepository.findById(data.idContratante())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contratante não identificado"));

        if (!verificarServicoEmAndamento(data.idContratante(), data.idFreelancer())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não há um serviço existente entre os dois usuários");
        }

        Servico servico = servicoRepository.getByUsers(freelancer, contratante);
        servico.setValorPagamento(data.valorHora());
        servico.setStatus("Concluído");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = LocalDate.now().format(dateTimeFormatter);
        servico.setFinishedAt(LocalDate.parse(dataString, dateTimeFormatter));

        servicoRepository.save(servico);
    }

    public void fecharServico(Long idContratante, Long idFreelancer) {
        Freelancer freelancer = freelancerRepository.findById(idFreelancer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Freelancer não identificado"));
        Contratante contratante = contratanteRepository.findById(idContratante)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contratante não identificado"));

        if (!verificarServicoEmAndamento(idContratante, idFreelancer)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não há um serviço existente entre os dois usuários");
        }

        var servico = servicoRepository.getByUsers(freelancer, contratante);
        servico.setStatus("Fechado");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = LocalDate.now().format(dateTimeFormatter);
        servico.setFinishedAt(LocalDate.parse(dataString, dateTimeFormatter));
        
        servicoRepository.save(servico);
    }

    public boolean verificarServicoEmAndamento(Long idContratante, Long idFreelancer) {
        Freelancer freelancer = freelancerRepository.findById(idFreelancer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Freelancer não identificado"));
        Contratante contratante = contratanteRepository.findById(idContratante)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contratante não identificado"));
        return servicoRepository.existsByEstadoEmAndamento(freelancer, contratante);
    }
}
