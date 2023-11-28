package com.devhub.api.service;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.dto.ContratanteValidacaoDTO;
import com.devhub.api.domain.contratante.dto.CreateContratanteDTO;
import com.devhub.api.domain.contratante.dto.ListContratanteDTO;
import com.devhub.api.domain.contratante.dto.UpdateContratanteDTO;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.freelancer.dto.FreelancerValidacaoDTO;
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
public class ContratanteService {

    @Autowired
    private ContratanteRepository repository;
    @Autowired
    private FreelancerRepository freelancerRepository;

    @Transactional
    public Contratante cadastrarContratante(CreateContratanteDTO data) {

        List<FreelancerValidacaoDTO> dadosFreelancer = freelancerRepository.validarDadosUnicos();
        List<ContratanteValidacaoDTO> dadosContratante = repository.validarDadosUnicos();

        List<Object> contasCadastradas = new ArrayList<>();
        contasCadastradas.addAll(dadosFreelancer);
        contasCadastradas.addAll(dadosContratante);

        var camposJaCadastrados = validarCamposCadastrados(contasCadastradas, data);

        if (!camposJaCadastrados.equals("Dados já cadastrados: ")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, camposJaCadastrados);
        }


        var contratante = new Contratante(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        contratante.setSenha(encryptedPassword);

        repository.save(contratante);

        return contratante;
    }

    protected String validarCamposCadastrados(List<Object> contasCadastradas, CreateContratanteDTO data) {
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
            } else if (conta instanceof ContratanteValidacaoDTO c) {
                if (c.getEmail().equalsIgnoreCase(data.email())) {
                    listaCampos.add("E-mail");
                }
                if (c.getTelefone().equalsIgnoreCase(data.telefone())) {
                    listaCampos.add("Telefone");
                }
                if (c.getCnpj().equalsIgnoreCase(data.cnpj())) {
                    listaCampos.add("CNPJ");
                }
            }
        }
        String campos = String.join(" | ", listaCampos);
        return camposJaCadastrados += campos;
    }

    public Page<ListContratanteDTO> getContratantes(Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(ListContratanteDTO::new);
        return page;
    }

    public Contratante getContratanteById(Long id) {
        var contratante = repository.getReferenceById(id);
        if (contratante == null) {
            throw new EntityNotFoundException();
        }
        return contratante;
    }

    @Transactional
    public Contratante atualizar(UpdateContratanteDTO data, Long id) {
        var contratante = repository.getReferenceById(id);
        if (contratante == null) {
            throw new EntityNotFoundException();
        }
        contratante.atuallizarInformacoes(data);
        return contratante;
    }

    public void excluir(Long id) {
        var contratante = repository.getReferenceById(id);
        if (contratante == null) {
            throw new EntityNotFoundException();
        }
        contratante.excluir();
    }

    public Integer atualizarFoto(MultipartFile novaFoto, Integer idFreelancer) throws IOException {
        byte[] novaFotoByte = novaFoto.getBytes();
        int atualizados = repository.atualizarFoto(novaFotoByte, idFreelancer);

        int status = atualizados == 1 ? 200 : 404;

        return status;
    }
}
