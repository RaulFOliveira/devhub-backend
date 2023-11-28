package com.devhub.api.service;

import com.devhub.api.FilaObj;
import com.devhub.api.ListaObj;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FilaObj<Publicacao> fila;
    public PublicacaoService(PublicacaoRepository repository, ContratanteRepository contratanteRepository, EspecialidadeDesejadaRepository especialidadeDesejadaRepository) {
        this.repository = repository;
        this.contratanteRepository = contratanteRepository;
        this.especialidadeDesejadaRepository = especialidadeDesejadaRepository;
        this.fila = new FilaObj<>(15);
    }

    @Transactional
    public Publicacao realizarPublicacao(CreatePublicacaoDTO data, Long id) {
        var contratante = contratanteRepository.getReferenceById(id);

        if (contratante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar um contratante com ID: " + id);
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
        var contratante = contratanteRepository.findById(id);

        if (contratante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar um contratante com ID: " + id);
        }

        var publicacoes = repository.findByContratante(contratante.get());

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

    @Transactional
    public void deletarPublicacao(Long id) {
        var publicacao = repository.findById(id);
        if (publicacao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        especialidadeDesejadaRepository.deleteAllByFkPublicacao(publicacao.get());
        repository.delete(publicacao.get());
    }

    public void enfileirarPublicacoes(List<CreatePublicacaoDTO> publicacaoDTOS, Long id) {


        var contratante = contratanteRepository.findById(id);
        if (contratante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar um contratante com ID: " + id);
        }

        for (CreatePublicacaoDTO publicacaoDTO : publicacaoDTOS) {
            fila.insert(new Publicacao(publicacaoDTO, contratante.get()));
        }

    }

    public List<Publicacao> realizarPublicacoesAgendadas(Integer qtdPublicacoes) {
        if (fila.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não há nada para ser publicado!");
        }

        if (qtdPublicacoes > fila.getTamanho()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não há publicações o suficiente para publicar nessa quantidade. Publicações agendadas: " +fila.getTamanho());
        }

        List<Publicacao> publicacoesASeremPostadas = new ArrayList<>();
        for (int i = 0; i < qtdPublicacoes; i++) {
            fila.exibe();
            var publicacao = fila.poll();
            if (publicacao != null) {
                publicacoesASeremPostadas.add(publicacao);
            }
        }
        repository.saveAll(publicacoesASeremPostadas);
        return publicacoesASeremPostadas;
    }
}
