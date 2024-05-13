package com.devhub.api.service;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.dto.ContratanteValidacaoDTO;
import com.devhub.api.domain.contratante.dto.CreateContratanteDTO;
import com.devhub.api.domain.contratante.dto.ListContratanteDTO;
import com.devhub.api.domain.contratante.dto.UpdateContratanteDTO;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.freelancer.dto.FreelancerValidacaoDTO;
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

    public List<ListContratanteDTO> getContratantes() {
        List<Contratante > contratantes = repository.findAllByAtivoTrue();
        List<ListContratanteDTO> dtos = contratantes.stream().map(c -> new ListContratanteDTO(
                        c.getId(), c.getNome(), c.getCnpj(),
                        c.getTelefone(), c.getEmail(), c.getImagem(),
                        c.getContratacoes()
                )).toList();
        return dtos;
    }

    public ListContratanteDTO getContratanteById(Long id) {
        var contratante = repository.getReferenceById(id);
        if (contratante == null) {
            throw new EntityNotFoundException();
        }
        return new ListContratanteDTO(
                contratante.getId(), contratante.getNome(), contratante.getCnpj(),
                contratante.getTelefone(), contratante.getEmail(), contratante.getImagem(),
                contratante.getContratacoes());
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

    public byte[] getFoto(int codigo) {
        var imagem = repository.getImagemById(codigo);
        return imagem;
    }
}
