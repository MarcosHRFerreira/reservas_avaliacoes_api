package postech.fiap.com.br.reservas_avaliacoes_api.service.cozinhas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.*;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CozinhaServiceTest {

    @Mock
    private CozinhaRepository cozinhaRepository;

    @Mock
    private CozinhaServiceImpl cozinhaService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        cozinhaService = new CozinhaServiceImpl(cozinhaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarCozinha {
        @Test
        void cadastrar_deveRetornarOk_quandoCadastroValido() {
            // Arrange
            CozinhaEntity cozinhaEntity = new CozinhaEntity();
            cozinhaEntity.setEspecialidade("Cozinha Brasileira");
            when(cozinhaRepository.existsByEspecialidade(cozinhaEntity.getEspecialidade())).thenReturn(false);

            // Act
            ResponseEntity<Object> response = cozinhaService.cadastrar(cozinhaEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertTrue(response.hasBody());

            verify(cozinhaRepository, times(1)).save(any(cozinhaEntity.getClass()));
        }

        @Test
        void cadastrar_quandoEspecialidadeJaExiste_deveRetornarBadRequest() {
            // Arrange
            CozinhaEntity cozinhaEntity = new CozinhaEntity();
            cozinhaEntity.setEspecialidade("Cozinha Brasileira");
            when(cozinhaRepository.existsByEspecialidade(cozinhaEntity.getEspecialidade())).thenReturn(true);

            // Act
            ResponseEntity<Object> response = cozinhaService.cadastrar(cozinhaEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Erro ao cadastrar : Especialidade já existe na base cozinha");

            verify(cozinhaRepository, never()).save(cozinhaEntity);
        }
    }

    @Nested
    class AlterarCozinha {

        @Test
        void atualizar_quandoCozinhaExiste_deveRetornarOk() {
            // Arrange
            Long idCozinha = 1L;
            DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto = new DadosAtualizacaoCozinhaDto(idCozinha, "Nova Especialidade");
            CozinhaEntity cozinhaEntity = new CozinhaEntity(idCozinha, "Especialidade");
            when(cozinhaRepository.existsById(idCozinha)).thenReturn(true);
            when(cozinhaRepository.getReferenceById(idCozinha)).thenReturn(cozinhaEntity);

            // Act
            ResponseEntity<Object> response = cozinhaService.atualizar(dadosAtualizacaoCozinhaDto);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoCozinhaDto(cozinhaEntity), response.getBody());
            verify(cozinhaRepository).existsById(idCozinha);
            verify(cozinhaRepository).getReferenceById(idCozinha);

        }

        @Test
        void atualizar_quandoCozinhaNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idCozinha = 1L;
            DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto = new DadosAtualizacaoCozinhaDto(idCozinha, "Nova Especialidade");
            when(cozinhaRepository.existsById(idCozinha)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = cozinhaService.atualizar(dadosAtualizacaoCozinhaDto);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id da Cozinha informado não existe!", response.getBody());
            verify(cozinhaRepository).existsById(idCozinha);
            verify(cozinhaRepository, never()).getReferenceById(idCozinha);
        }
    }

    @Nested
    class PaginarCozinha {

        @Test
        void obterPaginados_quandoPaginacaoValida_deveRetornarPaginaDeCozinhas() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10, Sort.by("especialidade").ascending());
            List<CozinhaEntity> cozinhas = new ArrayList<>();
            cozinhas.add(new CozinhaEntity(1L, "Especialidade 1"));
            cozinhas.add(new CozinhaEntity(2L, "Especialidade 2"));
            Page<CozinhaEntity> page = new org.springframework.data.domain.PageImpl<>(cozinhas, pageable, cozinhas.size());
            when(cozinhaRepository.findAll(any(Pageable.class))).thenReturn(page);

            // Act
            Page<CozinhaEntity> result = cozinhaService.obterPaginados(pageable);

            // Assert
            assertEquals(page, result);
            verify(cozinhaRepository).findAll(any(Pageable.class));
        }
    }

    @Nested
    class ConsultarCodigoCozinha {

        @Test
        void obterPorCodigo_quandoCozinhaExiste_deveRetornarOk() {
            // Arrange
            Long codigo = 1L;
            CozinhaEntity cozinhaEntity = new CozinhaEntity(codigo, "Especialidade");
            when(cozinhaRepository.existsById(codigo)).thenReturn(true);
            when(cozinhaRepository.getReferenceById(codigo)).thenReturn(cozinhaEntity);

            // Act
            ResponseEntity<Object> response = cozinhaService.obterPorCodigo(codigo);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoCozinhaDto(cozinhaEntity), response.getBody());
            verify(cozinhaRepository).existsById(codigo);
            verify(cozinhaRepository).getReferenceById(codigo);
        }
    }
}
