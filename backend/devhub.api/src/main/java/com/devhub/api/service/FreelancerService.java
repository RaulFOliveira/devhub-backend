package com.devhub.api.service;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.ContratanteValidacaoDTO;
import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.especialidade.EspecialidadeData;
import com.devhub.api.domain.especialidade.EspecialidadeRepository;
import com.devhub.api.domain.freelancer.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private ContratanteRepository contratanteRepository;

    @Transactional
    public Freelancer cadastrarFreelancer(CreateFreelancerData data) {
        
        List<FreelancerValidacaoDTO> dadosFreelancer = repository.validarDadosUnicos();
        List<ContratanteValidacaoDTO> dadosContratante = contratanteRepository.validarDadosUnicos();

        List<Object> contasCadastradas = new ArrayList<>();
        contasCadastradas.addAll(dadosFreelancer);
        contasCadastradas.addAll(dadosContratante);

        var camposJaCadastrados = validarCamposCadastrados(contasCadastradas, data);

        if (!camposJaCadastrados.equals("Dados já cadastrados: ")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, camposJaCadastrados);
        }
        var freelancer = new Freelancer(data);
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        freelancer.setSenha(encryptedPassword);

        repository.save(freelancer);

        var listaEspecialidades = data.especialidades();

        for (EspecialidadeData dataEspec : listaEspecialidades) {
            var especialidade = new Especialidade(dataEspec, freelancer);
            especialidadeRepository.save(especialidade);
        }

        return freelancer;
    }

    protected String validarCamposCadastrados(List<Object> contasCadastradas, CreateFreelancerData data) {
        String camposJaCadastrados = "Dados já cadastrados: ";
        List<String> listaCampos = new ArrayList<>();
        for (Object conta: contasCadastradas) {
            if (conta instanceof FreelancerValidacaoDTO f) {
                if (f.getEmail().equalsIgnoreCase(data.email())) {
                    listaCampos.add("E-mail");
                }
                if (f.getTelefone().equalsIgnoreCase(data.telefone())) {
                    listaCampos.add("Telefone");
                }
                if (f.getCpf().equalsIgnoreCase(data.cpf())) {
                    listaCampos.add("CPF");
                }
            } else if (conta instanceof ContratanteValidacaoDTO c) {
                if (c.getEmail().equalsIgnoreCase(data.email())) {
                    listaCampos.add("E-mail");
                }
                if (c.getTelefone().equalsIgnoreCase(data.telefone())) {
                    listaCampos.add("Telefone");
                }
            }
        }
        String campos = String.join(" | ", listaCampos);
        return camposJaCadastrados += campos;
    }

    public Page<ListFreelancerData> getFreelancers(Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListFreelancerData::new);
        if (!page.hasContent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return page;
    }
    public Freelancer getFreelancerById(Long id) {
        return repository.getReferenceById(id);
    }

    public Freelancer atualizar(UpdateFreelancerData data, Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            return null;
        }
        freelancer.atuallizarInformacoes(data);
        return freelancer;
    }

    public Freelancer excluir(Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            return null;
        }
        freelancer.excluir();
        return freelancer;
    }
}
