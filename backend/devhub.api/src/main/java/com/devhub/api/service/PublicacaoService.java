package com.devhub.api.service;

import com.devhub.api.ListaObj;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacaoService {

    private PublicacaoRepository repository;
    private ContratanteRepository contratanteRepository;
    private EspecialidadeDesejadaRepository especialidadeDesejadaRepository;
    ListaObj<Publicacao> listaPublicacao = new ListaObj<>(100);

    public PublicacaoService(PublicacaoRepository repository, ContratanteRepository contratanteRepository, EspecialidadeDesejadaRepository especialidadeDesejadaRepository) {
        this.repository = repository;
        this.contratanteRepository = contratanteRepository;
        this.especialidadeDesejadaRepository = especialidadeDesejadaRepository;
    }

    public Publicacao realizarPublicacao(CreatePublicacaoDTO data, Long id) {
        var contratante = contratanteRepository.getReferenceById(id);

        if (contratante == null) {
            throw new EntityNotFoundException("Não foi possível encontrar um contratante com ID: " + id);
        }

        var publicacao = new Publicacao(data, contratante);
        repository.save(publicacao);

        var listaEspecialidades = data.especialidadesDesejadas();

        List<EspecialidadeDesejada> especialidadeDesejadas = new ArrayList<>();
        for (EspecialidadeDesejadaDTO dataEspec : listaEspecialidades) {
            especialidadeDesejadas.add(new EspecialidadeDesejada(dataEspec, publicacao));
        }
        especialidadeDesejadaRepository.saveAll(especialidadeDesejadas);

        return publicacao;
    }

    public List<Publicacao> mostrarPublicacoes() {
        var publicacoes = repository.findAllByOrderByCreatedAtDesc();

        if (publicacoes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

//        for (Publicacao publicacao: publicacoes) {
//            listaPublicacao.adiciona(publicacao);
//        }
//
//        for (int i = 0; i < listaPublicacao.getTamanho() - 1; i++) {
//            for (int j = 1; j < listaPublicacao.getTamanho()  - i; j++) {
//                if (listaPublicacao.getElemento(j).getCreatedAt().isBefore(listaPublicacao.getElemento(j-1).getCreatedAt())) {
//                    Publicacao aux = listaPublicacao.getElemento(j);
//                    listaPublicacao.substitui(j, listaPublicacao.getElemento(j-1));
//                    listaPublicacao.substitui(j-1, aux);
//                }
//            }
//        }

        return publicacoes;
    }
    public List<Publicacao> mostrarPublicacoesByid(Long id) {
        var contratante = contratanteRepository.getReferenceById(id);

        if (contratante.equals(null)) {
            throw new EntityNotFoundException("Não foi possível encontrar um contratante com ID: " + id);
        }

        var publicacoes = repository.findByContratante(contratante);

        if (publicacoes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

//        for (Publicacao publicacao: publicacoes) {
//            listaPublicacao.adiciona(publicacao);
//        }
//
//        for (int i = 0; i < listaPublicacao.getTamanho() - 1; i++) {
//            for (int j = 1; j < listaPublicacao.getTamanho()  - i; j++) {
//                if (listaPublicacao.getElemento(j).getCreatedAt().isBefore(listaPublicacao.getElemento(j-1).getCreatedAt())) {
//                    Publicacao aux = listaPublicacao.getElemento(j);
//                    listaPublicacao.substitui(j, listaPublicacao.getElemento(j-1));
//                    listaPublicacao.substitui(j-1, aux);
//                }
//            }
//        }

        return publicacoes;
    }

}
