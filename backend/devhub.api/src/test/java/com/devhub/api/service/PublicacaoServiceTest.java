package com.devhub.api.service;

import com.devhub.api.file.FilaObj;
import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.dto.CreateContratanteDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PublicacaoService.class)
class PublicacaoServiceTest {
//
//    @Autowired
//    private PublicacaoService service;
//    private Contratante contratanteMock;
//    private Publicacao publicacaoMock;
//    private Publicacao publicacaoMockNull;
//    private CreatePublicacaoDTO publicacaoDTOMock;
//    private EspecialidadeDesejada especialidadeDesejadaMock;
//    private EspecialidadeDesejada especialidadeDesejadaMock2;
//    @MockBean
//    private PublicacaoRepository repo;
//    @Test
//    @DisplayName("Mostrar publicações deve retornar uma lista vazia")
//    void mostrarPublicacoesListaVazia() {
//        when(repo.findAllByOrderByCreatedAtDesc()).thenReturn(this.listaPublicacoes);
//
//        var response = service.mostrarPublicacoes();
//
//        assertEquals(0 , response.size());
//    }
//    @MockBean
//    private ContratanteRepository contratanteRepo;
//    @MockBean
//    private EspecialidadeDesejadaRepository especialidadeDesejadaRepo;
//    private CreatePublicacaoDTO publicacaoDTOEspecMock;
//    @MockBean
//    private List<Publicacao> listaPublicacoes;
//
//    @BeforeEach
//    void setUp() {
//        CreateContratanteDTO contratanteDTO = new CreateContratanteDTO(
//                "nome_b5e0ad1379ec",
//                "07348672000131",
//                "11111111111",
//                "exemplo@email.com",
//                "exemploSenha"
//        );
//
//        EspecialidadeDesejadaDTO especialidadeDesejadaDTO = new EspecialidadeDesejadaDTO("Python");
//        EspecialidadeDesejadaDTO especialidadeDesejadaDTO2 = new EspecialidadeDesejadaDTO("Java");
//        List<EspecialidadeDesejadaDTO> especialidadeDesejadaDTOS = new ArrayList<>();
//        List<EspecialidadeDesejadaDTO> especialidadeDesejadaDTOSEspec = new ArrayList<>();
//        especialidadeDesejadaDTOS.add(especialidadeDesejadaDTO);
//        especialidadeDesejadaDTOS.add(especialidadeDesejadaDTO2);
//        this.especialidadeDesejadaMock = new EspecialidadeDesejada(
//                especialidadeDesejadaDTO, publicacaoMock
//        );
//        this.especialidadeDesejadaMock2 = new EspecialidadeDesejada(
//                especialidadeDesejadaDTO2, publicacaoMock
//        );
//
//        this.publicacaoDTOMock = new CreatePublicacaoDTO(
//                "titulo teste",
//                "descricao teste",
//                especialidadeDesejadaDTOS
//
//        );
//        this.publicacaoDTOEspecMock = new CreatePublicacaoDTO(
//                null,
//                null,
//                null
//
//        );
//        this.contratanteMock = new Contratante(contratanteDTO);
//        this.publicacaoMock = new Publicacao(publicacaoDTOMock, contratanteMock);
//        this.publicacaoMockNull = null;
//        this.listaPublicacoes = new ArrayList<>();
//    }
//
//    @Test
//    @DisplayName("Realizar publicação com sucesso")
//    void realizarPublicacao() {
//        Long id = 1L;
//        when(contratanteRepo.getReferenceById(id))
//                .thenReturn(contratanteMock);
//
//        List<EspecialidadeDesejadaDTO> listaEspecialidades = new ArrayList<>();
//        listaEspecialidades.add(new EspecialidadeDesejadaDTO("Python"));
//        listaEspecialidades.add(new EspecialidadeDesejadaDTO("Java"));
//
//        var publicacao = new Publicacao(
//                new CreatePublicacaoDTO("titulo teste","descricao teste", listaEspecialidades),
//                contratanteMock);
//        when(repo.save(publicacao)).thenReturn(publicacao);
//
//        List<EspecialidadeDesejada> especialidadeDesejadas = new ArrayList<>();
//        for (EspecialidadeDesejadaDTO dataEspec : listaEspecialidades) {
//            especialidadeDesejadas.add(new EspecialidadeDesejada(dataEspec, publicacao));
//        }
//        when(especialidadeDesejadaRepo.saveAll(especialidadeDesejadas)).thenReturn(especialidadeDesejadas);
//
//        service.realizarPublicacao(publicacaoDTOMock, id);
//
//        verify(repo, times(1)).save(any(Publicacao.class));
//        verify(contratanteRepo, times(1)).getReferenceById(id);
//        verify(especialidadeDesejadaRepo, times(1)).saveAll(anyList());
//
//        assertEquals(publicacaoMock.getId(), publicacao.getId());
//        assertEquals(especialidadeDesejadaMock.getId(), especialidadeDesejadas.get(0).getId());
//        assertEquals(especialidadeDesejadaMock2.getId(), especialidadeDesejadas.get(1).getId());
//    }
//
//    @Test
//    @DisplayName("Realizar publicação não deve encontrar id do contratante")
//    void realizarPublicacaoErroContratante() {
//        Long id = 12L;
//
//        when(contratanteRepo.getReferenceById(id))
//                .thenReturn(null);
//
//        var exception = assertThrows(MethodArgumentNotValidException.class,
//                () -> service.realizarPublicacao(publicacaoDTOMock, id));
//
//        assertEquals(404, exception.getStatusCode().value());
//    }
//
//    @Test
//    @DisplayName("Mostrar publicações deve ocorrer com sucesso")
//    void mostrarPublicacoes() {
//        this.listaPublicacoes.add(publicacaoMock);
//        this.listaPublicacoes.add(publicacaoMock);
//        when(repo.findAllByOrderByCreatedAtDesc()).thenReturn(listaPublicacoes);
//
//        verify(repo, times(1)).findAllByOrderByCreatedAtDesc();
//
//        assertNotNull(listaPublicacoes);
//        assertEquals(2, listaPublicacoes.size());
//    }
//    @Test
//    @DisplayName("Mostrar publicações por id deve ocorrer com sucesso")
//    void mostrarPublicacoesByid() {
//        listaPublicacoes.add(publicacaoMock);
//        listaPublicacoes.add(publicacaoMock);
//        listaPublicacoes.add(publicacaoMock);
//
//        Long id = 1L;
//        when(contratanteRepo.getReferenceById(id)).thenReturn(contratanteMock);
//
//        var publicacao = new Publicacao(
//                new CreatePublicacaoDTO("","", new ArrayList<>()),
//                contratanteMock);
//
//        // Configurar comportamento simulado do BindingResult
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        // Chamar o método que deve lançar a exceção
//        var exception = assertThrows(MethodArgumentNotValidException.class,
//                () -> service.realizarPublicacao(publicacaoDTOEspecMock, id));
//
//        assertEquals(400, exception.getStatusCode().value());
//        when(repo.findByContratante(contratanteMock))
//                .thenReturn(listaPublicacoes);
//
//        service.mostrarPublicacoesByid(id);
//
//        verify(contratanteRepo, times(1)).getReferenceById(id);
//        verify(repo, times(1)).findByContratante(contratanteMock);
//
//        assertNotNull(listaPublicacoes);
//        assertEquals(3, listaPublicacoes.size());
//        assertEquals(listaPublicacoes.get(0).getContratante().getId(),
//                contratanteMock.getId());
//        assertEquals(listaPublicacoes.get(1).getContratante().getId(),
//                contratanteMock.getId());
//        assertEquals(listaPublicacoes.get(2).getContratante().getId(),
//                contratanteMock.getId());
//    }
//
//    @Test
//    @DisplayName("Mostrar publicações por id não deve encontrar contratante com id digitado")
//    void mostrarPublicacoesByIdInexistente() {
//        listaPublicacoes.add(publicacaoMock);
//        listaPublicacoes.add(publicacaoMock);
//        listaPublicacoes.add(publicacaoMock);
//
//        Long id = 12L;
//        when(contratanteRepo.findById(id)).thenReturn(Optional.empty());
//
//        var response = assertThrows(ResponseStatusException.class,
//                () -> service.mostrarPublicacoesByid(id));
//
//        assertEquals(404, response.getStatusCode().value());
//    }
//
//    @Test
//    @DisplayName("Mostrar publicação por ID deve retornar uma lista vazia")
//    void MostrarPublicacoesByIdListaVazia() {
//        Long id = 2L;
//        when(contratanteRepo.findById(id))
//                .thenReturn(Optional.of(contratanteMock));
//
//        when(repo.findByContratante(contratanteMock))
//                .thenReturn(listaPublicacoes);
//
//        var response = service.mostrarPublicacoesByid(id);
//
//        verify(repo, times(1)).findByContratante(contratanteMock);
//        verify(contratanteRepo, times(1)).findById(id);
//
//        assertNotNull(Optional.of(contratanteMock));
//        assertEquals(0, response.size());
//    }
//
//    @Test
//    @DisplayName("Deletar publicacao deve ocorrer com sucesso")
//    void deletarPublicacao() {
//        Long id = 1L;
//        when(repo.findById(id))
//                .thenReturn(Optional.of(publicacaoMock));
//
//        service.deletarPublicacao(id);
//
//        verify(repo, times(1)).findById(id);
//        verify(especialidadeDesejadaRepo, times(1)).deleteAllByFkPublicacao(any(Publicacao.class));
//        verify(repo, times(1)).delete(any(Publicacao.class));
//    }
//
//    @Test
//    @DisplayName("Deletar publicação não deve encontrar o ID da publicação")
//    void deletarPublicacaoIdInexistente() {
//        Long id = 12L;
//        when(repo.findById(id))
//                .thenReturn(Optional.empty());
//
//        assertThrows(ResponseStatusException.class,
//                () -> service.deletarPublicacao(id));
//
//        verify(repo, times(1)).findById(id);
//        verify(especialidadeDesejadaRepo, times(0)).deleteAllByFkPublicacao(any(Publicacao.class));
//        verify(repo, times(0)).delete(any(Publicacao.class));
//    }
//
//    @Test
//    @DisplayName("Enfileirar publicações deve ocorrer com sucesso")
//    void enfileirarPublicacoes() {
//        Long id = 1L;
//        List<CreatePublicacaoDTO> publicacaoDTOS = Arrays.asList(publicacaoDTOMock, publicacaoDTOMock);
//        when(contratanteRepo.findById(id))
//                .thenReturn(Optional.of(contratanteMock));
//
//        service.enfileirarPublicacoes(publicacaoDTOS, id);
//
//        verify(contratanteRepo, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("Enfileirar publicações não deve encontrar o ID do contratante")
//    void enfileirarPublicacoesIdContratante() {
//        Long id = 1L;
//        List<CreatePublicacaoDTO> publicacaoDTOS = Arrays.asList(publicacaoDTOMock, publicacaoDTOMock);
//
//        when(contratanteRepo.findById(id))
//                .thenReturn(Optional.empty());
//
//        var response = assertThrows(ResponseStatusException.class,
//                () -> service.enfileirarPublicacoes(publicacaoDTOS, id));
//
//        verify(contratanteRepo, times(1)).findById(id);
//        assertEquals(404, response.getStatusCode().value());
//    }
//
//    @Test
//    @DisplayName("Enfileirar publicações deve estar com a fila cheia")
//    void enfileirarPublicacoesFilaCheia() {
//        Long id = 1L;
//        List<CreatePublicacaoDTO> publicacaoDTOS =
//                Arrays.asList(publicacaoDTOMock);
//
//
//        when(contratanteRepo.findById(id))
//                .thenReturn(Optional.of(contratanteMock));
//
//        int capacidadeDaFila = 15;
//        FilaObj<Publicacao> fila = new FilaObj<>(capacidadeDaFila);
//        for (int i = 0; i < capacidadeDaFila; i++) {
//            fila.insert(publicacaoMock);
//        }
//
//
//        var response = assertThrows(ResponseStatusException.class,
//                () -> service.enfileirarPublicacoes(publicacaoDTOS, id));
//
//        verify(contratanteRepo, times(1)).findById(id);
////        verify(fila, times(1)).insert(any(Publicacao.class));
////        verify(fila, times(1)).isFull();
//
//        assertEquals(422, response.getStatusCode().value());
//    }
//
//    @Test
//    @DisplayName("Realizar publicações agendadas deve ocorrer com sucesso")
//    void realizarPublicacoesAgendadas() {
////        fila.insert(publicacaoMock);
////        fila.insert(publicacaoMock);
////        fila.insert(publicacaoMock);
//

}