package postech.fiap.com.br.reservas_avaliacoes_api.service.restaurantecozinha;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.DadosAtualizacaoRestauranteCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.RestauranteCozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.RestauranteCozinhaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.RestauranteCozinhaServiceImpl;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RestauranteCozinhaTest {

    @Mock
    private RestauranteCozinhaRepository restauranteCozinhaRepository;
    @Mock
    private RestauranteCozinhaServiceImpl restauranteCozinhaService;
    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private CozinhaRepository cozinhaRepository;


    AutoCloseable openMocks;

    @BeforeEach
    void setUp(){
        openMocks = MockitoAnnotations.openMocks(this);
        restauranteCozinhaService= new RestauranteCozinhaServiceImpl(restauranteCozinhaRepository,cozinhaRepository,restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Nested
    class CadastrarRestauranteCozinha{

        @Test
        void cadastrar_restauranteCozinhaValido_retornaOk() {
            // Arrange
            RestauranteCozinhaEntity restauranteCozinhaEntity = new RestauranteCozinhaEntity();
            restauranteCozinhaEntity.setIdrestaurante(1L);
            restauranteCozinhaEntity.setIdcozinha(1L);

            Mockito.when(restauranteRepository.existsById(restauranteCozinhaEntity.getIdrestaurante())).thenReturn(true);
            Mockito.when(cozinhaRepository.existsById(restauranteCozinhaEntity.getIdcozinha())).thenReturn(true);
            Mockito.when(restauranteCozinhaRepository.findByidrestauranteAndidcozinha(restauranteCozinhaEntity.getIdrestaurante(), restauranteCozinhaEntity.getIdcozinha())).thenReturn(false);
            Mockito.when(restauranteCozinhaRepository.save(restauranteCozinhaEntity)).thenReturn(restauranteCozinhaEntity);

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.cadastrar(restauranteCozinhaEntity);

            // Assert
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());

        }
        @Test
        void cadastrar_idRestauranteInvalido_retornaNotFound() {
            // Arrange
            RestauranteCozinhaEntity restauranteCozinhaEntity = new RestauranteCozinhaEntity();
            restauranteCozinhaEntity.setIdrestaurante(1L);
            restauranteCozinhaEntity.setIdcozinha(1L);

            Mockito.when(restauranteRepository.existsById(restauranteCozinhaEntity.getIdrestaurante())).thenReturn(false); // Mock do repositório

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.cadastrar(restauranteCozinhaEntity);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id do Restaurante informado não existe!", response.getBody());
        }

        @Test
        void cadastrar_restauranteCozinhaJaCadastrado_retornaBadRequest() {
            // Arrange
            RestauranteCozinhaEntity restauranteCozinhaEntity = new RestauranteCozinhaEntity();
            restauranteCozinhaEntity.setIdrestaurante(1L);
            restauranteCozinhaEntity.setIdcozinha(1L);

            Mockito.when(cozinhaRepository.existsById(restauranteCozinhaEntity.getIdcozinha())).thenReturn(true);
            Mockito.when(restauranteRepository.existsById(restauranteCozinhaEntity.getIdrestaurante())).thenReturn(true);
            Mockito.when(restauranteCozinhaRepository.findByidrestauranteAndidcozinha(restauranteCozinhaEntity.getIdrestaurante(), restauranteCozinhaEntity.getIdcozinha())).thenReturn(true);

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.cadastrar(restauranteCozinhaEntity);

            // Assert
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("Já existe um registro com este ID de restaurante e ID de cozinha.", response.getBody());
        }

    }
    @Nested
    class AtualizarRestauranteCozinha{

        @Test
        void atualizar_restauranteCozinhaValido_retornaOk() {
            // Arrange
            Long idRestauranteCozinha = 1L;
            DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto =
                    new DadosAtualizacaoRestauranteCozinhaDto(1L,1L,2L);

            RestauranteCozinhaEntity restauranteCozinhaEntity = new RestauranteCozinhaEntity();
            restauranteCozinhaEntity.setIdrestaurante(1L);
            restauranteCozinhaEntity.setIdcozinha(1L);

            Mockito.when(restauranteCozinhaRepository.existsById(idRestauranteCozinha)).thenReturn(true);
            Mockito.when(restauranteRepository.existsById(1L)).thenReturn(true);
            Mockito.when(cozinhaRepository.existsById(2L)).thenReturn(true);
            Mockito.when(restauranteCozinhaRepository.getReferenceById(idRestauranteCozinha)).thenReturn(restauranteCozinhaEntity);

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.atualizar(dadosAtualizacaoRestauranteCozinhaDto);

            // Assert
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());

        }

        @Test
        void atualizar_restauranteInvalido_retornaNotFound() {
            // Arrange
            Long idRestauranteCozinha = 1L;
            DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto =
                    new DadosAtualizacaoRestauranteCozinhaDto(1L,1L,2L);

            Mockito.when(restauranteCozinhaRepository.existsById(idRestauranteCozinha)).thenReturn(true); // Mock do repositório
            Mockito.when(restauranteRepository.existsById(999L)).thenReturn(false); // Mock do repositório
            Mockito.when(cozinhaRepository.existsById(2L)).thenReturn(true); // Mock do repositório

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.atualizar(dadosAtualizacaoRestauranteCozinhaDto);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id do Restaurante informado não existe!", response.getBody());
        }

        @Test
        void atualizar_cozinhaInvalida_retornaNotFound() {
            // Arrange
            Long idRestauranteCozinha = 1L;
            DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto =
                    new DadosAtualizacaoRestauranteCozinhaDto(1L,1L,2L);

            Mockito.when(restauranteCozinhaRepository.existsById(idRestauranteCozinha)).thenReturn(true);
            Mockito.when(restauranteRepository.existsById(1L)).thenReturn(true); // Mock do repositório
            Mockito.when(cozinhaRepository.existsById(999L)).thenReturn(false); // Mock do repositório

            // Act
            ResponseEntity<Object> response = restauranteCozinhaService.atualizar(dadosAtualizacaoRestauranteCozinhaDto);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id da Cozinha informado não existe!", response.getBody());
        }

    }
    @Nested
    class ConsultarRestauranteCozinha{

        @Test
        void obterPorCodigo_codigoValido_retornaOk() {
            // Arrange
            Long codigo = 1L;
            RestauranteCozinhaEntity restauranteCozinhaEntity = new RestauranteCozinhaEntity();
            restauranteCozinhaEntity.setIdrestaurante(1L);
            restauranteCozinhaEntity.setIdcozinha(1L);

            Mockito.when(restauranteCozinhaRepository.existsById(codigo)).thenReturn(true);
            Mockito.when(restauranteCozinhaRepository.getReferenceById(codigo)).thenReturn(restauranteCozinhaEntity);

            // Act
            ResponseEntity response = restauranteCozinhaService.obterPorCodigo(codigo);

            // Assert
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());

        }
        @Test
        void obterPorCodigo_codigoInvalido_retornaNotFound() {
            // Arrange
            Long codigo = 999L;
            Mockito.when(restauranteCozinhaRepository.existsById(codigo)).thenReturn(false); // Mock do repositório

            // Act
            ResponseEntity response = restauranteCozinhaService.obterPorCodigo(codigo);

            // Assert
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            Assertions.assertEquals("Id do Restaurante_Cozinha informado não existe!", response.getBody());
        }

    }
    @Nested
    class PaginarRestauranteCozinha{

        @Test
        void obterPaginados_pageableValido_retornaOk() {
            // Arrange
            int pageNumber = 0;
            int pageSize = 10;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            List<RestauranteCozinhaEntity> restaurantecozinha = new ArrayList<>();
            Page<RestauranteCozinhaEntity> pageMock = new PageImpl<>(restaurantecozinha, pageable, restaurantecozinha.size());

            // Configure o mock do repositório para retornar o pageMock
            when(restauranteCozinhaRepository.findAll(pageable)).thenReturn(pageMock);

            // Act
            Page<RestauranteCozinhaEntity> resultado = restauranteCozinhaService.obterPaginados(pageable);

            // Assert
            assertEquals(pageMock, resultado);
            verify(restauranteCozinhaRepository, times(1)).findAll(pageable);

        }

    }

}
