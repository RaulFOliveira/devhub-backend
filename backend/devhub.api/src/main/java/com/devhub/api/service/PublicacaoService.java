package com.devhub.api.service;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.freelancer.dto.ListaFreelancerDTO;
import com.devhub.api.domain.funcao.Funcao;
import com.devhub.api.domain.publicacao.dto.DetailPublicacaoDTO;
import com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO;
import com.devhub.api.domain.servico.CreateServicoDTO;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.usuario.UserRole;
import com.devhub.api.domain.usuario.Usuario;
import com.devhub.api.file.FilaObj;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import com.devhub.api.file.PilhaObj;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PublicacaoService {

    private PublicacaoRepository repository;
    @Autowired
    private ContratanteRepository contratanteRepository;
    @Autowired
    private FreelancerRepository freelancerRepository;
    private FilaObj<Publicacao> fila;
    private PilhaObj<String> pilha = new PilhaObj<>(15);
    public PublicacaoService(PublicacaoRepository repository) {
        this.repository = repository;
        this.fila = new FilaObj<>(15);
    }

    @Transactional
    public Publicacao realizarPublicacao(CreatePublicacaoDTO data, Long id) {

        if (data.role().equals("FREELANCER")){
            var freelancer = freelancerRepository.getById(id);
            if (freelancer.getId() == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não foi possível encontrar um freelancer com ID: " + id);
            }
        }

        if (data.role().equals("CONTRATANTE")){
            var contratante = contratanteRepository.getById(id);
            if (contratante.getId() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não foi possível encontrar um contratante com ID: " + id);
            }
        }

        var publicacao = new Publicacao(data);
        repository.save(publicacao);

        return publicacao;
    }

    public List<ListaPublicacaoDTO> mostrarPublicacoes() {
//        var publicacoes = repository.findAllByOrderByCreatedAtDesc();
//        List<ListaPublicacaoDTO> todasAsPublis = new ArrayList<>();
//        for (int i = 0; i < publicacoes.size(); i++){
//            Long idUsuario = publicacoes.get(i).getId_usuario();
//            ListaPublicacaoDTO consultaDaVez;
//            if (publicacoes.get(i).getRole().equals("CONTRATANTE")){
//                consultaDaVez = repository.findByContratante(idUsuario).get(0); // Pega o primeiro elemento da lista
//            }else{
//                consultaDaVez = repository.findByFreelancer(idUsuario).get(0); // Pega o primeiro elemento da lista
//            }
//            todasAsPublis.add(consultaDaVez);
//        }
//        System.out.println(todasAsPublis);
//        return todasAsPublis;

            return repository.findAllPublicacoes();

    }

    public List<Publicacao> mostrarPublicacoesByid(Long id) {
        var contratante = contratanteRepository.findById(id);

        if (contratante.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não foi possível encontrar um contratante com ID: " + id);
        }

        var publicacoes = repository.findAll();
        return publicacoes;
    }

    @Transactional
    public void deletarPublicacao(Long id) {
        var publicacao = repository.findById(id);
        if (publicacao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.delete(publicacao.get());
    }

//    public void enfileirarPublicacoes(List<CreatePublicacaoDTO> publicacaoDTOS, Long id) {
//
//
//        var contratante = contratanteRepository.findById(id);
//        if (contratante.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    "Não foi possível encontrar um contratante com ID: " + id);
//        }
//
//        for (CreatePublicacaoDTO publicacaoDTO : publicacaoDTOS) {
//            fila.insert(new Publicacao(publicacaoDTO, contratante.get()));
//        }
//
//    }

//    public List<Publicacao> realizarPublicacoesAgendadas(Integer qtdPublicacoes) {
//        if (fila.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    "Não há nada para ser publicado!");
//        }
//
//        if (qtdPublicacoes > fila.getTamanho()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "Não há publicações o suficiente para publicar nessa quantidade. Publicações agendadas: " +fila.getTamanho());
//        }
//
//        List<Publicacao> publicacoesASeremPostadas = new ArrayList<>();
//        for (int i = 0; i < qtdPublicacoes; i++) {
//            fila.exibe();
//            var publicacao = fila.poll();
//            if (publicacao != null) {
//                publicacoesASeremPostadas.add(publicacao);
//            }
//        }
//        repository.saveAll(publicacoesASeremPostadas);
//        return publicacoesASeremPostadas;
//    }

//    public void publicarEmLote(MultipartFile arquivoTxt, Long id) throws IOException {
//        InputStream inputStream = arquivoTxt.getInputStream();
//        Stream<String> txt = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
//                .lines();
//
//        var contratante = contratanteRepository.findById(id);
//        for (String linha  : txt.toList()) {
//            if (linha.substring(0, 2).equals("00")) {
//                System.out.println("É um linha de header");
//            } else if (linha.substring(0, 2).equals("01")) {
//                System.out.println("É um linha de trailer");
//            } else if (linha.substring(0, 2).equals("02")) {
//                System.out.println("É um linha de corpo");
//
//                String titulo = linha.substring(2, 49).trim();
//                String descricao = linha.substring(49, 149).trim();
//                var especialidadesString = linha.substring(149, 249);
//                var especialidades = especialidadesString.split(";");
//
//                List<EspecialidadeDesejadaDTO> dtos = new ArrayList<>();
//                for (String especialidade : especialidades) {
//                    dtos.add(new EspecialidadeDesejadaDTO(especialidade));
//                }
//                Publicacao p = new Publicacao(new CreatePublicacaoDTO(titulo, descricao, dtos), contratante.get());
//                repository.save(p);
//                for (EspecialidadeDesejadaDTO dto : dtos) {
//                    EspecialidadeDesejada especialidadeDesejada =
//                            new EspecialidadeDesejada(dto, p);
//                    especialidadeDesejadaRepository.save(especialidadeDesejada);
//                }
//            } else {
//                System.out.println("Registro inválido");
//            }
//        }
//
//        pilha.push(arquivoTxt.getName());
//    }
}
