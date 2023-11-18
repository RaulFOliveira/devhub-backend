package com.devhub.api.service;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.contratante.ContratanteRepository;
import com.devhub.api.domain.contratante.dto.CreateContratanteDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejada;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaDTO;
import com.devhub.api.domain.especialidade_desejada.EspecialidadeDesejadaRepository;
import com.devhub.api.domain.publicacao.Publicacao;
import com.devhub.api.domain.publicacao.PublicacaoRepository;
import com.devhub.api.domain.publicacao.dto.CreatePublicacaoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PublicacaoService.class)
class PublicacaoServiceTest {

    @Autowired
    private PublicacaoService service;
    private Contratante contratanteMock;
    private Publicacao publicacaoMock;
    private CreatePublicacaoDTO publicacaoDTOMock;
    private EspecialidadeDesejada especialidadeDesejadaMock;
    private EspecialidadeDesejada especialidadeDesejadaMock2;
    @MockBean
    private PublicacaoRepository repo;
    @MockBean
    private ContratanteRepository contratanteRepo;
    @MockBean
    private EspecialidadeDesejadaRepository especialidadeDesejadaRepo;
    private CreatePublicacaoDTO publicacaoDTOEspecMock;

    @BeforeEach
    void setUp() {
        CreateContratanteDTO contratanteDTO = new CreateContratanteDTO(
                "nome_b5e0ad1379ec",
                "07348672000131",
                "11111111111",
                "exemplo@email.com",
                "exemploSenha"
        );

        EspecialidadeDesejadaDTO especialidadeDesejadaDTO = new EspecialidadeDesejadaDTO("Python");
        EspecialidadeDesejadaDTO especialidadeDesejadaDTO2 = new EspecialidadeDesejadaDTO("Java");
        List<EspecialidadeDesejadaDTO> especialidadeDesejadaDTOS = new ArrayList<>();
        List<EspecialidadeDesejadaDTO> especialidadeDesejadaDTOSEspec = new ArrayList<>();
        especialidadeDesejadaDTOS.add(especialidadeDesejadaDTO);
        especialidadeDesejadaDTOS.add(especialidadeDesejadaDTO2);
        this.especialidadeDesejadaMock = new EspecialidadeDesejada(
                especialidadeDesejadaDTO, publicacaoMock
        );
        this.especialidadeDesejadaMock2 = new EspecialidadeDesejada(
                especialidadeDesejadaDTO2, publicacaoMock
        );

        this.publicacaoDTOMock = new CreatePublicacaoDTO(
                "titulo teste",
                "descricao teste",
                especialidadeDesejadaDTOS

        );
        this.publicacaoDTOEspecMock = new CreatePublicacaoDTO(
                null,
                null,
                null

        );
        this.contratanteMock = new Contratante(contratanteDTO);
        this.publicacaoMock = new Publicacao(publicacaoDTOMock, contratanteMock);
    }

    @Test
    @DisplayName("Realizar publicação com sucesso")
    void realizarPublicacao() {
        Long id = 1L;
        when(contratanteRepo.getReferenceById(id))
                .thenReturn(contratanteMock);

        List<EspecialidadeDesejadaDTO> listaEspecialidades = new ArrayList<>();
        listaEspecialidades.add(new EspecialidadeDesejadaDTO("Python"));
        listaEspecialidades.add(new EspecialidadeDesejadaDTO("Java"));

        var publicacao = new Publicacao(
                new CreatePublicacaoDTO("titulo teste","descricao teste", listaEspecialidades),
                contratanteMock);
        when(repo.save(publicacao)).thenReturn(publicacao);

        List<EspecialidadeDesejada> especialidadeDesejadas = new ArrayList<>();
        for (EspecialidadeDesejadaDTO dataEspec : listaEspecialidades) {
            especialidadeDesejadas.add(new EspecialidadeDesejada(dataEspec, publicacao));
        }
        when(especialidadeDesejadaRepo.saveAll(especialidadeDesejadas)).thenReturn(especialidadeDesejadas);

        service.realizarPublicacao(publicacaoDTOMock, id);

        verify(repo, times(1)).save(any(Publicacao.class));
        verify(contratanteRepo, times(1)).getReferenceById(id);
        verify(especialidadeDesejadaRepo, times(1)).saveAll(anyList());

        assertEquals(publicacaoMock.getId(), publicacao.getId());
        assertEquals(especialidadeDesejadaMock.getId(), especialidadeDesejadas.get(0).getId());
        assertEquals(especialidadeDesejadaMock2.getId(), especialidadeDesejadas.get(1).getId());
    }

    @Test
    @DisplayName("Realizar publicação não deve encontrar id do contratante")
    void realizarPublicacaoErroContratante() {
        Long id = 12L;
        when(contratanteRepo.getReferenceById(id))
                .thenReturn(null);

        var exception = assertThrows(ResponseStatusException.class,
                () -> service.realizarPublicacao(publicacaoDTOMock, id));

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    @DisplayName("Realizar publicação não tem especialidades desejadas")
    void realizarPublicacaoNulos() {
        Long id = 1L;
        when(contratanteRepo.getReferenceById(id))
                .thenReturn(contratanteMock);

        var publicacao = new Publicacao(
                new CreatePublicacaoDTO("","", new ArrayList<>()),
                contratanteMock);

        // Configurar comportamento simulado do BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Chamar o método que deve lançar a exceção
        var exception = assertThrows(MethodArgumentNotValidException.class,
                () -> service.realizarPublicacao(publicacaoDTOEspecMock, id));

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void mostrarPublicacoes() {
    }

    @Test
    void mostrarPublicacoesByid() {
    }

    @Test
    void deletarPublicacao() {
    }

    @Test
    void enfileirarPublicacoes() {
    }

    @Test
    void realizarPublicacoesAgendadas() {
    }
}