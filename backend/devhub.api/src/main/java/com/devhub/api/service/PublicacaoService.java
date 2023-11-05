package com.devhub.api.service;

import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

@Service
public class PublicacaoService {

    private PublicacaoRepository repository;
    private ContratanteRepository contratanteRepository;
    private EspecialidadeDesejadaRepository especialidadeDesejadaRepository;

    public PublicacaoService(PublicacaoRepository repository, ContratanteRepository contratanteRepository) {
        this.repository = repository;
        this.contratanteRepository = contratanteRepository;
    }

    public Publicacao realizarPublicacao(CreatePublicacaoDTO data, Long id) {
        var contratante = contratanteRepository.getReferenceById(id);

        if (contratante == null) {
            throw new EntityNotFoundException("Não foi possível encontrar um contratante com ID: " + id);
        }

        var publicacao = new Publicacao(data, contratante);
        repository.save(publicacao);

        var listaEspecialidades = data.especialidadesDesejadas();
        for (EspecialidadeDesejadaDTO dataEspec : listaEspecialidades) {
            var especialidade = new EspecialidadeDesejada(dataEspec, publicacao);
            especialidadeDesejadaRepository.save(especialidade);
        }

        return publicacao;
    }
}
