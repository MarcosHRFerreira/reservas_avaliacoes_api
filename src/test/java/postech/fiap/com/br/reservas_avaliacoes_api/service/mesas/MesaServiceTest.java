package postech.fiap.com.br.reservas_avaliacoes_api.service.mesas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MesaServiceTest {

    @Mock
    private MesaRepository mesaRepository;
    @Mock
    private MesaServiceImpl mesaService;
    @Mock
    private RestauranteRepository restauranteRepository;

    AutoCloseable openMocks;


    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mesaService = new MesaServiceImpl(mesaRepository, restauranteRepository);
    }
    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarMesa {
        @Test
        void cadastrar_deveRetornarNotFound_quandoIdRestauranteNaoExiste() {
            // Arrange
            MesaEntity mesaEntity = new MesaEntity(1L, 1L, "1", Status_Mesa.DISPONIVEL);
            when(restauranteRepository.existsById(1L)).thenReturn(false);
            // Act
            ResponseEntity<Object> response = mesaService.cadastrar(mesaEntity);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isEqualTo("Id do Restaurante informado não existe!");
        }

        @Test
        void cadastrar_deveRetornarBadRequest_quandoMesaJaExiste() {
            // Arrange
            MesaService mesaServico = Mockito.mock(MesaService.class);
            MesaEntity mesaEntity = new MesaEntity(1L, 1L, "1", Status_Mesa.DISPONIVEL);
            when(mesaServico.cadastrar(mesaEntity)).thenReturn(ResponseEntity.badRequest().body("Mesa já existe")); // Configure o mock para retornar BAD_REQUEST
            // Act
            ResponseEntity<Object> response = mesaServico.cadastrar(mesaEntity);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Mesa já existe");
        }

        @Test
        void cadastrar_deveRetornarOk_quandoMesaCriadaComSucesso() {
            // Arrange
            MesaService mesaServico = Mockito.mock(MesaService.class);
            MesaEntity mesaEntity = new MesaEntity(1L, 1L, "1", Status_Mesa.DISPONIVEL);
            when(mesaServico.cadastrar(mesaEntity)).thenReturn(ResponseEntity.ok().build()); // Configure o mock para retornar OK
            // Act
            ResponseEntity<Object> response = mesaServico.cadastrar(mesaEntity);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            verify(mesaServico,times(1)).cadastrar(any(mesaEntity.getClass()));
        }
    }

    @Nested
    class AlterarMesa {
        @Test
        void deveAtualizarMesaComSucesso() {
            // Arrange
            MesaRepository mesaRepositorio = Mockito.mock(MesaRepository.class);
            MesaService mesaServico = Mockito.mock(MesaService.class);

            DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto = new DadosAtualizacaoMesaDto(1L, 1L, "4", Status_Mesa.DISPONIVEL);
            MesaEntity mesa = new MesaEntity(1L, 1L, "4", Status_Mesa.DISPONIVEL);

            when(mesaRepositorio.getReferenceById(dadosAtualizacaoMesaDto.idmesa())).thenReturn(mesa);
            when(mesaRepositorio.findByidrestauranteAndidmesa(dadosAtualizacaoMesaDto.idrestaurante(), dadosAtualizacaoMesaDto.idmesa())).thenReturn(true);
            when(mesaServico.atualizar(dadosAtualizacaoMesaDto)).thenReturn(ResponseEntity.ok(new DadosDetalhamentoMesaDto(mesa)));
            // Act
            ResponseEntity<Object> response = mesaServico.atualizar(dadosAtualizacaoMesaDto);
            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody() instanceof DadosDetalhamentoMesaDto);

            verify(mesaServico,times(1)).atualizar(any(dadosAtualizacaoMesaDto.getClass()));
        }

        @Test
        void deveRetornarNotFoundQuandoMesaNaoExiste() {
            // Arrange
            MesaRepository mesaRepositorio = Mockito.mock(MesaRepository.class);
            MesaServiceImpl mesaServico = Mockito.mock(MesaServiceImpl.class);
            DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto = new DadosAtualizacaoMesaDto(10L, 1L, "1", Status_Mesa.DISPONIVEL);

            when(mesaRepositorio.existsById(dadosAtualizacaoMesaDto.idmesa())).thenReturn(false);
            doReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id da Mesa informado não existe!")).when(mesaServico).atualizar(any(DadosAtualizacaoMesaDto.class));
            // Act
            ResponseEntity<Object> response = mesaServico.atualizar(dadosAtualizacaoMesaDto);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isEqualTo("Id da Mesa informado não existe!");
            verify(mesaServico,times(1)).atualizar(any(dadosAtualizacaoMesaDto.getClass()));

        }
        @Test
        void deveRetornarBadRequestQuandoRestauranteOuMesaInvalido() {

            MesaRepository mesaRepositorio = Mockito.mock(MesaRepository.class);
            MesaServiceImpl mesaServico = Mockito.mock(MesaServiceImpl.class);
            // Arrange
            DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto = new DadosAtualizacaoMesaDto(15L, 15L, "7", Status_Mesa.DISPONIVEL);
            Mockito.when(mesaRepositorio.findByidrestauranteAndidmesa(
                    dadosAtualizacaoMesaDto.idrestaurante(),
                    dadosAtualizacaoMesaDto.idmesa()
            )).thenReturn(false); // Simula que não existe um registro com o ID do restaurante e número da mesa

            doReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe um registro com ID de restaurante e número de mesa.")).when(mesaServico).atualizar(any(DadosAtualizacaoMesaDto.class));
            // Act
            ResponseEntity<Object> response = mesaServico.atualizar(dadosAtualizacaoMesaDto);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isEqualTo("Não existe um registro com ID de restaurante e número de mesa.");
            verify(mesaServico,times(1)).atualizar(any(dadosAtualizacaoMesaDto.getClass()));
        }
    }
    @Nested
    class ExcluirMesa {

        @Test
        void deveExcluirMesaComSucesso() {
            // Arrange
            MesaRepository mesaRepositorio = Mockito.mock(MesaRepository.class);
            RestauranteRepository restaurante = Mockito.mock(RestauranteRepository.class);
            MesaServiceImpl mesaServico = new MesaServiceImpl(mesaRepositorio, restaurante);
            Long codigo = 1L;
            // Simulando a exclusão da mesa com sucesso
            doNothing().when(mesaRepository).deleteById(codigo);
            // Act
            ResponseEntity<Void> response = mesaServico.excluirMesa(codigo);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
    }

    @Nested
    class PaginarMesa {

        @Test
        void deveRetornarListaDeMesasPaginada() {
            MesaRepository mesaRepositorio = Mockito.mock(MesaRepository.class);
            RestauranteRepository restaurante = Mockito.mock(RestauranteRepository.class);
            MesaServiceImpl mesaServico = new MesaServiceImpl(mesaRepositorio, restaurante);

            Page<MesaEntity> page = new PageImpl<>(Arrays.asList(
                    new MesaEntity(1L, 1L, "Mesa 1", Status_Mesa.DISPONIVEL),
                    new MesaEntity(1L, 2L, "Mesa 2", Status_Mesa.DISPONIVEL)
            ));

            // Mock do método findAll do mesaRepositorio
            when(mesaRepositorio.findAll(any(Pageable.class))).thenReturn(page);

            Page<MesaEntity> mesas = mesaServico.obterPaginados(PageRequest.of(0, 10));

            assertThat(mesas).hasSize(2);

            assertThat(mesas.getContent()).allSatisfy(mesa -> {
                assertThat(mesa).isNotNull();
                assertThat(mesa).isInstanceOf(MesaEntity.class);
            });

            Mockito.verify(mesaRepositorio, times(1)).findAll(any(Pageable.class));
        }
    }
}
