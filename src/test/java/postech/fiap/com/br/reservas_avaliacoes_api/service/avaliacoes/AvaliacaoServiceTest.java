package postech.fiap.com.br.reservas_avaliacoes_api.service.avaliacoes;


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
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;
    @Mock
    private AvaliacaoServiceImpl avaliacaoService;
    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository, restauranteRepository, clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarAvaliacao {

        @Test
        void cadastrar_deveRetornarOk_quandoAvaliacaoValida() {
            // Arrange
            AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity(); // Crie um objeto de teste válido
            avaliacaoEntity.setidcliente(1L);
            avaliacaoEntity.setidrestaurante(2L);
            avaliacaoEntity.setdataavaliacao(LocalDateTime.now()); // Defina uma data válida

            when(clienteRepository.existsById(avaliacaoEntity.getidcliente())).thenReturn(true);
            when(restauranteRepository.existsById(avaliacaoEntity.getidrestaurante())).thenReturn(true);
            when(avaliacaoRepository.findByid_clienteAndid_restauranteAnddata_avaliacao(
                    avaliacaoEntity.getidcliente(),
                    avaliacaoEntity.getidrestaurante(),
                    avaliacaoEntity.getdataavaliacao().toLocalDate())).thenReturn(false); // Simule que não existe avaliação com essa data

            when(avaliacaoRepository.save(avaliacaoEntity)).thenReturn(avaliacaoEntity); // Simule o salvamento
            // Act
            ResponseEntity<Object> response = avaliacaoService.cadastrar(avaliacaoEntity);
            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertTrue(response.hasBody());
            verify(avaliacaoRepository, times(1)).save(any(avaliacaoEntity.getClass()));
        }

        @Test
        void cadastrar_deveRetornarBadRequest_quandoClienteNaoExiste() {
            // Arrange
            AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
            avaliacaoEntity.setidcliente(1L);
            avaliacaoEntity.setidrestaurante(2L);
            avaliacaoEntity.setdataavaliacao(LocalDateTime.now());

            when(clienteRepository.existsById(avaliacaoEntity.getidcliente())).thenReturn(false);

            // Act
            ResponseEntity<Object> response = avaliacaoService.cadastrar(avaliacaoEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Erro ao cadastrar: Id do Cliente informado não existe!");
            verify(avaliacaoRepository, times(0)).save(any(avaliacaoEntity.getClass()));

        }

        @Test
        void cadastrar_deveRetornarBadRequest_quandoRestauranteNaoExiste() {
            // Arrange
            AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
            avaliacaoEntity.setidcliente(1L);
            avaliacaoEntity.setidrestaurante(12L);
            avaliacaoEntity.setdataavaliacao(LocalDateTime.now());

            when(clienteRepository.existsById(avaliacaoEntity.getidcliente())).thenReturn(true);
            when(restauranteRepository.existsById(avaliacaoEntity.getidrestaurante())).thenReturn(false);

            // Act
            ResponseEntity<Object> response = avaliacaoService.cadastrar(avaliacaoEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Erro ao cadastrar: Id do Restaurante informado não existe!");

            verify(avaliacaoRepository, times(0)).save(any(avaliacaoEntity.getClass()));

        }

        @Test
        void cadastrar_deveRetornarBadRequestParaAvaliacaoExistente() {
            // Arrange
            AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();

            avaliacaoEntity.setidcliente(1L);
            avaliacaoEntity.setidrestaurante(1L);
            avaliacaoEntity.setdataavaliacao(LocalDateTime.now());

            when(clienteRepository.existsById(avaliacaoEntity.getidcliente())).thenReturn(true);
            when(restauranteRepository.existsById(avaliacaoEntity.getidrestaurante())).thenReturn(true);
            when(avaliacaoRepository.findByid_clienteAndid_restauranteAnddata_avaliacao(
                    avaliacaoEntity.getidcliente(),
                    avaliacaoEntity.getidrestaurante(),
                    avaliacaoEntity.getdataavaliacao().toLocalDate())).thenReturn(true);
            // Act
            ResponseEntity<Object> response = avaliacaoService.cadastrar(avaliacaoEntity);
            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Erro ao cadastrar: Já existe um registro com este ID do cliente e ID do restaurante e Data Avaliação.", response.getBody());

            verify(avaliacaoRepository, times(0)).save(any(avaliacaoEntity.getClass()));

        }

    }
    @Nested
    class AtualizarAvaliacao{

//        @Test
//        void atualizar_deveAtualizarAvaliacaoComSucesso() {
//            // Arrange
//            DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvaliacao = new DadosAtualizacaoAvaliacaoDto(1L,1L,1L,7L,"NOTA 7",LocalDateTime.now());
//            AvaliacaoEntity avaliacao = new AvaliacaoEntity(1L,1L,1L,7L,"NOTA 7",LocalDateTime.now());
//            when(avaliacaoRepository.existsById(dadosAtualizacaoAvaliacao.idcliente())).thenReturn(true);
//            when(avaliacaoRepository.existsById(dadosAtualizacaoAvaliacao.idrestaurante())).thenReturn(true);
//            when(avaliacaoRepository.existsById(dadosAtualizacaoAvaliacao.idavaliacao())).thenReturn(true);
//            when(avaliacaoRepository.getReferenceById(dadosAtualizacaoAvaliacao.idavaliacao())).thenReturn(avaliacao);
//
//            // Act
//            ResponseEntity<Object> response = avaliacaoService.atualizar(dadosAtualizacaoAvaliacao);
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        }

    }
    @Nested
    class PaginarAvaliacao{

        @Test
        void obterPaginados_deveRetornarPaginaDeAvaliacoes() {
            // Arrange
            int pageNumber = 0;
            int pageSize = 10;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            List<AvaliacaoEntity> avaliacoes = new ArrayList<>();
            Page<AvaliacaoEntity> pageMock = new PageImpl<>(avaliacoes, pageable, avaliacoes.size());
            when(avaliacaoRepository.findAll(pageable)).thenReturn(pageMock);
            // Act
            Page<AvaliacaoEntity> resultado = avaliacaoService.obterPaginados(pageable);
            // Assert
            assertEquals(pageMock, resultado);
            verify(avaliacaoRepository, times(1)).findAll(pageable);
        }

    }
    @Nested
    class ConsultarAvaliacao {

        @Test
        void obterPorCodigo_deveRetornarAvaliacaoExistente() {
            // Mock do repositório
            AvaliacaoRepository avaliacaoRepository = mock(AvaliacaoRepository.class);
            Long codigo = 1L;
            AvaliacaoEntity avaliacao = new AvaliacaoEntity(); // Criar uma avaliação de exemplo
            when(avaliacaoRepository.existsById(codigo)).thenReturn(true); // Simular a existência da avaliação
            when(avaliacaoRepository.getReferenceById(codigo)).thenReturn(avaliacao); // Simular a busca da avaliação

            // Criar o serviço
            AvaliacaoServiceImpl avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepository,restauranteRepository,clienteRepository);

            // Executar a busca
            ResponseEntity<DadosDetalhamentoAvalizacaoDto> response = avaliacaoService.obterPorCodigo(codigo);

            // Verificar se a resposta é OK
            assertEquals(HttpStatus.OK, response.getStatusCode());

            // Verificar se os dados da avaliação estão corretos
            DadosDetalhamentoAvalizacaoDto dados = response.getBody();
            assertEquals(avaliacao.getidavaliacao(), dados.idavaliacao());
            assertEquals(avaliacao.getidrestaurante(), dados.idrestaurante());
        }
    }
    @Nested
    class ExcluirAvaliacao{

        @Test
         void excluirAvaliacaoSucesso() throws Exception {

            AvaliacaoRepository avaliacaoRepositor = Mockito.mock(AvaliacaoRepository.class);
            RestauranteRepository restaurante = Mockito.mock(RestauranteRepository.class);
            AvaliacaoServiceImpl avaliacaoServico = new AvaliacaoServiceImpl(avaliacaoRepository, restauranteRepository, clienteRepository);
            Long codigo = 1L;

            when(avaliacaoRepositor.existsById(codigo)).thenReturn(true);

            // Simulando a exclusão da mesa com sucesso
            doNothing().when(avaliacaoRepositor).deleteById(codigo);

        }

    }

}
