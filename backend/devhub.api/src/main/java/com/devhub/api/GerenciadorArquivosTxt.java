package com.devhub.api;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.freelancer.Freelancer;
import com.devhub.api.domain.freelancer.FreelancerRepository;
import com.devhub.api.domain.servico.CreateServicoData;
import com.devhub.api.domain.servico.Servico;
import com.devhub.api.domain.servico.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivosTxt {

    @Autowired
    private static ServicoRepository repository;
    @Autowired
    private static ContratanteRepository contratanteRepository;
    @Autowired
    private static FreelancerRepository freelancerRepository;

    static List<Servico> listaLida = new ArrayList<>();

    public static void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

// Abre o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq,true));
        }
        catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo");
        }

// Grava o registro e fecha o arquivo
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
        }
    }

    public static void gravaArquivoTxt(List<Servico> lista, String nomeArq) {
        int contaRegDados = 0;

        // Monta o registro de header
        String header = "00SERVICO20232"; //Verificar documento de layout
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // Grava o registro de header
        gravaRegistro(header, nomeArq);

        // Grava os registros de dados (ou registros de corpo)
        for (Servico s : lista) {
            String corpo = "02";


            //Gravando corpo no arquivo:
            gravaRegistro(corpo, nomeArq);
            // Incrementa o contador de registros de dados gravados
            contaRegDados++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d", contaRegDados);

        gravaRegistro(trailer, nomeArq);
    }

    public static void leArquivoTxt(String nomeArq) {

        BufferedReader entrada = null;

        String registro, tipoRegistro;

        int contaRegDadosLidos = 0;

        int qtdRegDadosGravados;


        listaLida.clear();

        // Abre o arquivo

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        }
        catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo");
        }



        // Leitura do arquivo
        try {

            registro = entrada.readLine();

            Freelancer freelancer = null;
            Contratante contratante = null;
            Integer horasTrabalhadas = null;
            Integer contadorServicos = 0;
            while (registro != null) {

                // obtem os 2 primeiros caracteres do registro lido

                // 1o argumento do substring é o indice do que se quer obter, iniciando de zero

                // 2o argumento do substring é o indice final do que se deseja, MAIS UM

                // 012345

                // 00NOTA

                tipoRegistro = registro.substring(0,2);


                if (tipoRegistro.equals("00")) {
                    System.out.println("É um registro de header");

                    //Exibir informações do header
                    System.out.println("Tipo do arquivo " + registro.substring(0,2));
                    System.out.println("Serviço: " + registro.substring(2,9));
                    System.out.println("Data e hora " + registro.substring(9,28));
                    System.out.println("Versão do Layout " + registro.substring(28,30));
                }

                else if (tipoRegistro.equals("01")) {
                    System.out.println("É um registro de trailer");
                    //Exibir quantidade de registros

                }
                else if (tipoRegistro.equals("02")) {
                    System.out.println("É um registro de corpo");

                    horasTrabalhadas = Integer.valueOf(registro.substring(2,5).trim());
                    System.out.println(horasTrabalhadas);
                    String cnpj = registro.substring(5,19);
                    System.out.println(cnpj);
                    contratante = contratanteRepository.findByCnpj(cnpj);

                    contaRegDadosLidos++;
                }
                else if (tipoRegistro.equals("03")) {

                    String nomeFreelancer = registro.substring(2,43).trim();
                    String telefone = registro.substring(43, 55);
                    String email = registro.substring(55, 106).trim();
                    String funcao = registro.substring(106, 137).trim();
                    Double valorHora = Double.valueOf(registro.substring(137, 144)
                            .trim()
                            .replace(",", "."));
                    String senioridade = registro.substring(144, 151).trim();
                    freelancer =
                            freelancerRepository.findByNomeAndTelefoneAndEmailAndValorHoraAndSenioridade(nomeFreelancer, telefone, email, valorHora, senioridade);
                    contaRegDadosLidos++;
                }
                else {

                    System.out.println("Registro inválido");

                }

                if (contaRegDadosLidos % 2 == 0 && contaRegDadosLidos > 0) {
                    contadorServicos++;
                }

                if (contaRegDadosLidos / 2 == contadorServicos) {
                    listaLida.add(
                            new Servico(
                                    new CreateServicoData(horasTrabalhadas), contratante, freelancer
                            )
                    );
                }

                // Le o proximo registro

                registro = entrada.readLine();



            }  // fim do while

            // Fecha o arquivo

            entrada.close();

        } // fim do try
        catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo");
            erro.printStackTrace();
        }

        // Exibe a lista lida

        System.out.println("\nLista lida do arquivo:");

        for (Servico s : listaLida) {
            System.out.println(s);
        }
        // Aqui tb seria possível salvar a lista no BD

         repository.saveAll(listaLida);
    }
}
