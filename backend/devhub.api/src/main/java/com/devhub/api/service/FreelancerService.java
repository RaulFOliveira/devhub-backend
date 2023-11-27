package com.devhub.api.service;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.dto.ContratanteValidacaoDTO;
import com.devhub.api.domain.especialidade.Especialidade;
import com.devhub.api.domain.especialidade.EspecialidadeDTO;
import com.devhub.api.domain.especialidade.EspecialidadeRepository;
import com.devhub.api.domain.freelancer.*;
import com.devhub.api.domain.freelancer.dto.CreateFreelancerDTO;
import com.devhub.api.domain.freelancer.dto.FreelancerValidacaoDTO;
import com.devhub.api.domain.freelancer.dto.ListFreelancerDTO;
import com.devhub.api.domain.freelancer.dto.UpdateFreelancerDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository repository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private ContratanteRepository contratanteRepository;

    @Transactional
    public Freelancer cadastrarFreelancer(CreateFreelancerDTO data) {
        
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
        return freelancer;
    }

    protected String validarCamposCadastrados(List<Object> contasCadastradas, CreateFreelancerDTO data) {
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

    public Page<ListFreelancerDTO> getFreelancers(Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListFreelancerDTO::new);
        if (!page.hasContent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return page;
    }
    public Freelancer getFreelancerById(Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            throw new EntityNotFoundException();
        }
        return freelancer;
    }

    @Transactional
    public Freelancer atualizar(UpdateFreelancerDTO data, Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            throw new EntityNotFoundException();
        }
        freelancer.atuallizarInformacoes(data);
        return freelancer;
    }

    @Transactional
    public void excluir(Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            throw new EntityNotFoundException();
        }
        freelancer.excluir();
    }

    public List<Especialidade> cadastrarEspecialidades(List<String> lista, Long id) {
        var freelancer = repository.getReferenceById(id);
        if (freelancer == null) {
            throw new EntityNotFoundException();
        }


        List<Especialidade> especialidades = new ArrayList<>();
        for (String especialidade : lista) {
            especialidades.add(new Especialidade(especialidade, freelancer));
        }
        especialidadeRepository.saveAll(especialidades);
        return especialidades;
    }

    public Integer atualizarFoto(MultipartFile novaFoto, Integer idFreelancer) throws IOException {
        byte[] novaFotoByte = novaFoto.getBytes();
        int atualizados = repository.atualizarFoto(novaFotoByte, idFreelancer);

        int status = atualizados == 1 ? 200 : 404;

        return status;
    }
}
