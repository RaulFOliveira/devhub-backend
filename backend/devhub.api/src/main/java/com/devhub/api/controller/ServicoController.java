package com.devhub.api.controller;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaData;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.publicacao.CreatePublicacaoData;
import com.devhub.api.domain.publicacao.DetailPublicacaoData;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.servico.CreateServicoData;
import com.devhub.api.domain.servico.DetailServicoData;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.servico.ServicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private ContratanteRepository contratanteRepository;


    @PostMapping
    @Transactional
    public ResponseEntity criarPublicacao(@RequestBody @Valid CreateServicoData data, UriComponentsBuilder uriBuilder) {

       var freelancer = freelancerRepository.findById();
       var contratante = contratanteRepository.findById();

        var servico = new Servico(data);

        servicoRepository.save(servico);

        var uri = uriBuilder.path("/publicacoes/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailServicoData(servico));
    }

    @GetMapping
    public ResponseEntity<List<Servico>> mostrarServicos(){
        List<Servico> servicos = servicoRepository.findAll();

        if (servicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(servicos);
    }
}
