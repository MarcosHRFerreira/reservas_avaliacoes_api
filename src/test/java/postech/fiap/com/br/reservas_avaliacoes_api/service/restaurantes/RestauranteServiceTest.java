package postech.fiap.com.br.reservas_avaliacoes_api.service.restaurantes;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteServiceImpl restauranteService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp(){
        openMocks= MockitoAnnotations.openMocks(this);
        restauranteService = new RestauranteServiceImpl(restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Nested
    class CadastrarRestaurante{

        @Test
        void cadastrar_quandoRestauranteNaoExiste_deveRetornarOk() {

            // Arrange
            RestauranteEntity restauranteEntity = new RestauranteEntity();
            restauranteEntity.setNome("Restaurante do Ze");
            restauranteEntity.setEmail("restauranteze@teste.com.br");
            restauranteEntity.setLogradouro("rua do ze");
            restauranteEntity.setBairro("bairro do ze");
            restauranteEntity.setCep("12345678");
            restauranteEntity.setUf("SP");
            restauranteEntity.setCidade("Cidade do Ze");
            restauranteEntity.setNumero("2");
            restauranteEntity.setTelefone("1234567890");
            restauranteEntity.setFuncionamento("08:00 as 17:00");
            restauranteEntity.setCapacidade("100 lugares");

            when(restauranteRepository.existsBynomeAndEmail(restauranteEntity.getNome(), restauranteEntity.getEmail())).thenReturn(false);
            when(restauranteRepository.existsByEmail(restauranteEntity.getEmail())).thenReturn(false);
            when(restauranteRepository.save(restauranteEntity)).thenReturn(restauranteEntity);

            // Act
            ResponseEntity<Object> response = restauranteService.cadastrar(restauranteEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertTrue(response.hasBody());
            verify(restauranteRepository).existsBynomeAndEmail(restauranteEntity.getNome(), restauranteEntity.getEmail());
            verify(restauranteRepository).existsByEmail(restauranteEntity.getEmail());
            verify(restauranteRepository).save(restauranteEntity);
        }
        @Test
        void cadastrar_quandoRestauranteJaExisteComEmail_deveRetornarBadRequest() {
            // Arrange
            RestauranteEntity restauranteEntity = new RestauranteEntity();
            restauranteEntity.setNome("Restaurante do Ze");
            restauranteEntity.setEmail("restauranteze@teste.com.br");
            restauranteEntity.setLogradouro("rua do ze");
            restauranteEntity.setBairro("bairro do ze");
            restauranteEntity.setCep("12345678");
            restauranteEntity.setUf("SP");
            restauranteEntity.setCidade("Cidade do Ze");
            restauranteEntity.setNumero("2");
            restauranteEntity.setTelefone("1234567890");
            restauranteEntity.setFuncionamento("08:00 as 17:00");
            restauranteEntity.setCapacidade("100 lugares");
            restauranteEntity.setCozinha("japonesa");

            when(restauranteRepository.existsBynomeAndEmail(restauranteEntity.getNome(), restauranteEntity.getEmail())).thenReturn(false);
            when(restauranteRepository.existsByEmail(restauranteEntity.getEmail())).thenReturn(true);

            // Act
            ResponseEntity<Object> response = restauranteService.cadastrar(restauranteEntity);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

            assertThat(response.getBody()).isEqualTo("Erro ao cadastrar restaurante: Já existe o email " + restauranteEntity.getEmail() + " na base de restaurantes");

            verify(restauranteRepository, never()).save(restauranteEntity);
        }

    }
    @Nested
    class AlterarRestaurante{
        @Test
        void atualizar_quandoRestauranteExiste_deveRetornarOk() {
            // Arrange
            Long idRestaurante = 1L;
            DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto = new DadosAtualizacaoRestauranteDto(
                    idRestaurante,
                    "Restaurante do Zeze",
                    "restauranteze@teste.com.br",
                    "1234567890",
                    "rua do ze",
                    "bairro do ze",
                    "12345678",
                    "",
                    "2",
                    "SP",
                    "sao carlos",
                    "08:00 as 17:00",
                     "100 lugares",
                    "japonesa");

            RestauranteEntity restauranteEntity = new RestauranteEntity();
            restauranteEntity.setNome("Restaurante do Zeze");
            restauranteEntity.setEmail("restauransteze@teste.com.br");
            restauranteEntity.setLogradouro("rua do ze");
            restauranteEntity.setBairro("bairro do ze");
            restauranteEntity.setCep("12345678");
            restauranteEntity.setUf("SP");
            restauranteEntity.setCidade("Cidade do Ze");
            restauranteEntity.setNumero("2");
            restauranteEntity.setTelefone("1234567890");
            restauranteEntity.setFuncionamento("08:00 as 17:00");
            restauranteEntity.setCapacidade("100 lugares");
            restauranteEntity.setCozinha("japonesa");

            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
            when(restauranteRepository.getReferenceById(idRestaurante)).thenReturn(restauranteEntity);

            // Act
            ResponseEntity<Object> response = restauranteService.atualizar(dadosAtualizacaoRestauranteDto);

            // Assert
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        }

        @Test
        void atualizar_quandoRestauranteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idRestaurante = 1L;
            DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto = new DadosAtualizacaoRestauranteDto(
                    idRestaurante,
                    "Restaurante do Zeze",
                    "restauranteze@teste.com.br",
                    "1234567890",
                    "rua do ze",
                    "bairro do ze",
                    "12345678",
                    "",
                    "2",
                    "SP",
                    "sao carlos",
                    "08:00 as 17:00",
                    "100 lugares",
                    "japonesa");

            when(restauranteRepository.existsById(idRestaurante)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = restauranteService.atualizar(dadosAtualizacaoRestauranteDto);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id do Restaurante informado não existe!", response.getBody());
            verify(restauranteRepository).existsById(idRestaurante);
            verify(restauranteRepository, never()).getReferenceById(idRestaurante);
        }

    }
    @Nested
    class PaginarRestaurante{
        @Test
        void obterPaginados_quandoPaginacaoValida_deveRetornarPaginaDeRestaurantes() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
            List<RestauranteEntity> restaurantes = new ArrayList<>();

            restaurantes.add(new RestauranteEntity(1L, "Restaurante do Zeze","resssstauranteze@teste.com.br","1234567890","rua do ze","bairro do ze","12345678","","2","SP","sao carlos","08:00 as 17:00","100 lugares","japonesa"));
            restaurantes.add(new RestauranteEntity(2L, "Restaurante do Ze","restsauranteze@teste.com.br","1234567890","rua do ze","bairro do ze","12345678","","2","SP","sao carlos","08:00 as 17:00","100 lugares","japonsesa"));

            Page<RestauranteEntity> page = new org.springframework.data.domain.PageImpl<>(restaurantes, pageable, restaurantes.size());
            when(restauranteRepository.findAll(any(Pageable.class))).thenReturn(page);

            // Act
            Page<RestauranteEntity> resultado = restauranteService.obterPaginados(pageable);

            // Assert
            Assertions.assertEquals(page, resultado);
            verify(restauranteRepository).findAll(any(Pageable.class));
        }

    }
    @Nested
    class ConsultarRestaurante{
        @Test
        void obterPorCodigo_quandoRestauranteExiste_deveRetornarOk() {
            // Arrange
            Long codigo = 1L;
            RestauranteEntity restauranteEntity = new RestauranteEntity(codigo, "Restaurante do Zeze","resssstauranteze@teste.com.br","1234567890","rua do ze","bairro do ze","12345678","","2","SP","sao carlos","08:00 as 17:00","100 lugares","japonesa");
            when(restauranteRepository.existsById(codigo)).thenReturn(true);
            when(restauranteRepository.getReferenceById(codigo)).thenReturn(restauranteEntity);

            // Act
            ResponseEntity<Object> response = restauranteService.obterPorCodigo(codigo);

            // Assert
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(new DadosDetalhamentoRestauranteDto(restauranteEntity), response.getBody());

            verify(restauranteRepository).existsById(codigo);
            verify(restauranteRepository).getReferenceById(codigo);
        }

        @Test
        void obterPorCodigo_quandoRestauranteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long codigo = 1L;
            when(restauranteRepository.existsById(codigo)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = restauranteService.obterPorCodigo(codigo);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id do restaurante informado não existe!", response.getBody());
            verify(restauranteRepository).existsById(codigo);
            verify(restauranteRepository, never()).getReferenceById(codigo);
        }

    }
}
