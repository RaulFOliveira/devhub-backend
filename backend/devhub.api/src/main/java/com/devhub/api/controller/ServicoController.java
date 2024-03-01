package com.devhub.api.controller;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.funcao.Funcao;
//import com.devhub.api.domain.servico.CreateServicoDTO;
import com.devhub.api.domain.servico.DetailServicoDTO;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.servico.ServicoRepository;
import com.devhub.api.service.ServicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity criarServico(@RequestParam Long idContratante,
                                          @RequestParam Long idFreelancer,
                                          UriComponentsBuilder uriBuilder) {
        var servico = service.criarServico(idFreelancer, idContratante);
        var uri = uriBuilder.path("/servicos/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailServicoDTO(servico));
    }

    @PatchMapping("/concluir")
    public ResponseEntity concluirServico(@RequestParam Long idContratante,
                                          @RequestParam Long idFreelancer,
                                          @RequestBody Double valorPagamento) {
        service.concluirServico(idContratante, idFreelancer, valorPagamento);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/fechar")
    public ResponseEntity fecharServico(@RequestParam Long idContratante,
                                        @RequestParam Long idFreelancer) {
        service.fecharServico(idContratante, idFreelancer);
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public boolean validarAndamento(@RequestParam("idContratante") Long idContratante,
                                    @RequestParam("idFreelancer") Long idFreelancer) {
        return service.verificarServicoEmAndamento(idContratante, idFreelancer);
    }

//
//    @PostMapping("/txt")
//    public ResponseEntity criarServicoPorTxt(@RequestParam("file")MultipartFile arquivoTXT) throws IOException {
//        InputStream inputStream = arquivoTXT.getInputStream();
//        Stream<String> txt = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
//                .lines();
//
//        int cont = 0;
//        int contServico = 0;
//        int horasTrabalhadas = 0;
//        Freelancer freelancer = null;
//        Contratante contratante = null;
//        for (String linha  : txt.collect(Collectors.toList())) {
//            if (linha.substring(0,2).equals("00")) {
//                System.out.println("É um linha de header");
//            } else if (linha.substring(0,2).equals("01")) {
//                System.out.println("É um linha de trailer");
//            } else if (linha.substring(0,2).equals("02")) {
//                System.out.println("É um linha de corpo");
//
//                horasTrabalhadas = Integer.parseInt(linha.substring(2, 5).trim());
//                String cnpj = linha.substring(5,19);
//                contratante = contratanteRepository.findByCnpj(cnpj);
//                System.out.println(contratante);
//            } else if (linha.substring(0,2).equals("03")) {
//
//                String nomeFreelancer = linha.substring(2,43).trim();
//                System.out.println("nome: "+nomeFreelancer);
//                String telefone = linha.substring(43, 54);
//                System.out.println("telefone: "+telefone);
//                String email = linha.substring(54, 104).trim();
//                System.out.println("email: "+email);
//                String funcao = Funcao.valueOf(linha.substring(105, 137).trim()).getFuncao();
//                Double valorHora = Double.valueOf(linha.substring(137, 144)
//                        .trim()
//                        .replace(",", "."));
//                System.out.println("valor: "+ valorHora);
//                String senioridade = linha.substring(144, 152).trim();
//                System.out.println("senioridade: "+senioridade);
//                freelancer =
//                        freelancerRepository.findByNomeAndTelefoneAndEmailAndValorHoraAndSenioridade(nomeFreelancer, telefone, email, valorHora, senioridade);
//                System.out.println(freelancer);
//                cont+=2;
//            } else {
//                System.out.println("Registro inválido");
//            }
//
//            if (cont % 2 == 0 && cont > 0) {
//                listaLida.add(
//                        new Servico(
//                                new CreateServicoDTO(horasTrabalhadas), contratante, freelancer
//                        )
//                );
//            }
//        }
//        servicoRepository.saveAll(listaLida);
//        return ResponseEntity.ok().build();
//    }
}
