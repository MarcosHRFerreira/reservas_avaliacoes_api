package postech.fiap.com.br.reservas_avaliacoes_api.service.reservas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.Status_Mesa;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private ReservaServiceImpl reservaService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private MesaRepository mesaRepository;


    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        reservaService = new ReservaServiceImpl(reservaRepository, restauranteRepository, clienteRepository, mesaRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarReserva {

//        @Test
//        void cadastrar_quandoReservaValida_deveRetornarOk() {
//            // Arrange
//            Long idCliente = 2L;
//            Long idRestaurante = 3L;
//            LocalDateTime dataHora = LocalDateTime.now();
//
//            ReservaEntity reservaEntity = new ReservaEntity(3L, 2L, 3L, dataHora, 2, 1, Status_Reserva.RESERVADO);
//
//            ClienteEntity clienteEntity = new ClienteEntity();
//            clienteEntity.setNome("Ze");
//            clienteEntity.setEmail("ze@teste.com.br");
//            clienteEntity.setLogradouro("rua do ze");
//            clienteEntity.setBairro("bairro do ze");
//            clienteEntity.setCep("12345678");
//            clienteEntity.setUf("SP");
//            clienteEntity.setCidade("Cidade do Ze");
//            clienteEntity.setNumero("2");
//            clienteEntity.setTelefone("1234567890");
//
//            RestauranteEntity restauranteEntity = new RestauranteEntity();
//            restauranteEntity.setNome("Restaurante do Ze");
//            restauranteEntity.setEmail("restauranteze@teste.com.br");
//            restauranteEntity.setLogradouro("rua do ze");
//            restauranteEntity.setBairro("bairro do ze");
//            restauranteEntity.setCep("12345678");
//            restauranteEntity.setUf("SP");
//            restauranteEntity.setCidade("Cidade do Ze");
//            restauranteEntity.setNumero("2");
//            restauranteEntity.setTelefone("1234567890");
//
//            List<MesaEntity> mesasDisponiveis = new ArrayList<>();
//            mesasDisponiveis.add(new MesaEntity(1L, 1L, "4", Status_Mesa.DISPONIVEL));
//            mesasDisponiveis.add(new MesaEntity(2L, 1L, "3", Status_Mesa.DISPONIVEL));
//
//
//            when(clienteRepository.existsById(idCliente)).thenReturn(true);
//            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
//            when(reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate())).thenReturn(false);
//            when(mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.DISPONIVEL)).thenReturn(mesasDisponiveis);
//            when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);
//
//            // Act
//            ResponseEntity<Object> response = reservaService.cadastrar(reservaEntity);
//
//            // Assert
//            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//            assertTrue(response.hasBody());
//
//
//
//            verify(clienteRepository).existsById(idCliente);
//            verify(restauranteRepository).existsById(idRestaurante);
//            verify(reservaRepository).findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate());
//            verify(mesaRepository).findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.DISPONIVEL);
//            verify(reservaRepository).save(reservaEntity);
//            verify(mesaRepository, times(1)).save(any(MesaEntity.class));
//
//        }

        @Test
        void cadastrar_quandoClienteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idCliente = 5L;
            Long idRestaurante = 3L;
            LocalDateTime dataHora = LocalDateTime.now();

            ReservaEntity reservaEntity = new ReservaEntity(1L, 5L, 3L, dataHora, 2, 1, Status_Reserva.RESERVADO);

//            ClienteEntity clienteEntity = new ClienteEntity();
//            clienteEntity.setNome("Ze");
//            clienteEntity.setEmail("ze@teste.com.br");
//            clienteEntity.setLogradouro("rua do ze");
//            clienteEntity.setBairro("bairro do ze");
//            clienteEntity.setCep("12345678");
//            clienteEntity.setUf("SP");
//            clienteEntity.setCidade("Cidade do Ze");
//            clienteEntity.setNumero("2");
//            clienteEntity.setTelefone("1234567890");
//
//            RestauranteEntity restauranteEntity = new RestauranteEntity();
//            restauranteEntity.setNome("Restaurante do Ze");
//            restauranteEntity.setEmail("restauranteze@teste.com.br");
//            restauranteEntity.setLogradouro("rua do ze");
//            restauranteEntity.setBairro("bairro do ze");
//            restauranteEntity.setCep("12345678");
//            restauranteEntity.setUf("SP");
//            restauranteEntity.setCidade("Cidade do Ze");
//            restauranteEntity.setNumero("2");
//            restauranteEntity.setTelefone("1234567890");

            List<MesaEntity> mesasDisponiveis = new ArrayList<>();
            mesasDisponiveis.add(new MesaEntity(1L, 1L, "4", Status_Mesa.DISPONIVEL));
            mesasDisponiveis.add(new MesaEntity(2L, 1L, "3", Status_Mesa.DISPONIVEL));

            when(clienteRepository.existsById(idCliente)).thenReturn(false); // Simula que o cliente não existe
            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
            when(reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate())).thenReturn(false);
            when(mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.DISPONIVEL)).thenReturn(mesasDisponiveis);

            // Act
            ResponseEntity<Object> response = reservaService.cadastrar(reservaEntity);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id do Cliente informado não existe!", response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(restauranteRepository, never()).existsById(idRestaurante);
            verify(reservaRepository, never()).findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate());
            verify(mesaRepository, never()).findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.DISPONIVEL);
        }

        @Test
        void cadastrar_quandoRestauranteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idCliente = 2L;
            Long idRestaurante = 10L;
            LocalDateTime dataHora = LocalDateTime.now();

            ReservaEntity reservaEntity = new ReservaEntity(1L, 2L, 10L, dataHora, 2, 1, Status_Reserva.RESERVADO);

            ClienteEntity clienteEntity = new ClienteEntity();
            clienteEntity.setNome("Ze");
            clienteEntity.setEmail("ze@teste.com.br");
            clienteEntity.setLogradouro("rua do ze");
            clienteEntity.setBairro("bairro do ze");
            clienteEntity.setCep("12345678");
            clienteEntity.setUf("SP");
            clienteEntity.setCidade("Cidade do Ze");
            clienteEntity.setNumero("2");
            clienteEntity.setTelefone("1234567890");

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

            List<MesaEntity> mesasDisponiveis = new ArrayList<>();
            mesasDisponiveis.add(new MesaEntity(1L, 1L, "4", Status_Mesa.DISPONIVEL));
            mesasDisponiveis.add(new MesaEntity(2L, 1L, "3", Status_Mesa.DISPONIVEL));

            when(restauranteRepository.existsById(idRestaurante)).thenReturn(false);
            when(clienteRepository.existsById(idCliente)).thenReturn(true);
            when(reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate())).thenReturn(false);


            // Act
            ResponseEntity<Object> response = reservaService.cadastrar(reservaEntity);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id do Restaurante informado não existe!", response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(restauranteRepository).existsById(idRestaurante);
            verify(reservaRepository, never()).findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate());
            verify(mesaRepository, never()).findByStatusIsAndId_restaurante(anyLong(), any(Status_Mesa.class));
        }

        @Test
        void cadastrar_quandoReservaJaExiste_deveRetornarBadRequest() {
            // Arrange
            Long idCliente = 2L;
            Long idRestaurante = 3L;
            LocalDateTime dataHora = LocalDateTime.now();

            ReservaEntity reservaEntity = new ReservaEntity(3L, 2L, 3L, dataHora, 2, 1, Status_Reserva.RESERVADO);

            ClienteEntity clienteEntity = new ClienteEntity();
            clienteEntity.setNome("Ze");
            clienteEntity.setEmail("ze@teste.com.br");
            clienteEntity.setLogradouro("rua do ze");
            clienteEntity.setBairro("bairro do ze");
            clienteEntity.setCep("12345678");
            clienteEntity.setUf("SP");
            clienteEntity.setCidade("Cidade do Ze");
            clienteEntity.setNumero("2");
            clienteEntity.setTelefone("1234567890");

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

            when(clienteRepository.existsById(idCliente)).thenReturn(true);
            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
            when(reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate())).thenReturn(true); // Simula que a reserva já existe

            // Act
            ResponseEntity<Object> response = reservaService.cadastrar(reservaEntity);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Já existe uma reserva com este ID do cliente e ID do restaurante e Data da reserva.", response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(restauranteRepository).existsById(idRestaurante);
            verify(reservaRepository).findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate());
            verify(mesaRepository, never()).findByStatusIsAndId_restaurante(anyLong(), any(Status_Mesa.class)); // Verifica se as mesas não foram consultadas
        }

    }

    @Nested
    class AlterarReserva {

//        @Test
//        void atualizar_quandoReservaValida_deveRetornarOk() {
//            // Arrange
//            Long idCliente = 2L;
//            Long idRestaurante = 3L;
//            Long idReserva = 3L;
//            LocalDateTime dataHora = LocalDateTime.now();
//            DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto = new DadosAtualizacaoReservaDto(idReserva, idCliente, idRestaurante, dataHora, 1, 1, Status_Reserva.RESERVADO);
//
//            ReservaEntity reservaEntity = new ReservaEntity(3L, 2L, 3L, dataHora, 1, 1, Status_Reserva.RESERVADO);
//
//            ClienteEntity clienteEntity = new ClienteEntity();
//            clienteEntity.setNome("Zezao");
//            clienteEntity.setEmail("ze@teste.com.br");
//            clienteEntity.setLogradouro("rua do ze");
//            clienteEntity.setBairro("bairro do ze");
//            clienteEntity.setCep("12345678");
//            clienteEntity.setUf("SP");
//            clienteEntity.setCidade("Cidade do Ze");
//            clienteEntity.setNumero("2");
//            clienteEntity.setTelefone("1234567890");
//
//            RestauranteEntity restauranteEntity = new RestauranteEntity();
//            restauranteEntity.setNome("Restaurante do Ze");
//            restauranteEntity.setEmail("restauranteze@teste.com.br");
//            restauranteEntity.setLogradouro("rua do ze");
//            restauranteEntity.setBairro("bairro do ze");
//            restauranteEntity.setCep("12345678");
//            restauranteEntity.setUf("SP");
//            restauranteEntity.setCidade("Cidade do Ze");
//            restauranteEntity.setNumero("2");
//            restauranteEntity.setTelefone("1234567890");
//            restauranteEntity.setCozinha("japonesa");
//
//            when(clienteRepository.existsById(idCliente)).thenReturn(true);
//            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
//            when(reservaRepository.existsById(idReserva)).thenReturn(true);
//            when(reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate())).thenReturn(true);
//            when(reservaRepository.getReferenceById(idReserva)).thenReturn(reservaEntity);
//
//            // Act
//            ResponseEntity<Object> response = reservaService.atualizar(dadosAtualizacaoReservaDto);
//
//            // Assert
//            assertEquals(HttpStatus.OK, response.getStatusCode());
//            assertEquals(new DadosDetalhamentoReservaDto(reservaEntity), response.getBody());
//            verify(clienteRepository).existsById(idCliente);
//            verify(restauranteRepository).existsById(idRestaurante);
//            verify(reservaRepository).existsById(idReserva);
//            verify(reservaRepository).findByid_clienteAndid_restauranteAnddata_reserva(idCliente, idRestaurante, dataHora.toLocalDate());
//
//        }

        @Test
        void atualizar_quandoReservaNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idCliente = 1L;
            Long idRestaurante = 2L;
            Long idReserva = 2L;
            LocalDateTime dataHora = LocalDateTime.now();
            DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto = new DadosAtualizacaoReservaDto(idReserva, idCliente, idRestaurante, dataHora, 3, 2, Status_Reserva.FINALIZADO);

            when(clienteRepository.existsById(idCliente)).thenReturn(true);
            when(restauranteRepository.existsById(idRestaurante)).thenReturn(true);
            when(reservaRepository.existsById(idReserva)).thenReturn(false); // Simula que a reserva não existe

            // Act
            ResponseEntity<Object> response = reservaService.atualizar(dadosAtualizacaoReservaDto);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id da Reserva informado não existe!", response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(restauranteRepository).existsById(idRestaurante);
            verify(reservaRepository).existsById(idReserva);
            verify(reservaRepository, never()).findByid_clienteAndid_restauranteAnddata_reserva(anyLong(), anyLong(), any());
            verify(reservaRepository, never()).getReferenceById(anyLong());
        }

    }

    @Nested
    class PaginarReserva {

        @Test
        void obterPaginados_quandoPaginacaoValida_deveRetornarPaginaDeReservas() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10);
            List<ReservaEntity> reservas = new ArrayList<>();
            reservas.add(new ReservaEntity(1L, 2L, 3L, LocalDateTime.now(), 2, 1, Status_Reserva.RESERVADO));
            reservas.add(new ReservaEntity(2L, 2L, 3L, LocalDateTime.now(), 2, 1, Status_Reserva.FINALIZADO));
            Page<ReservaEntity> page = new org.springframework.data.domain.PageImpl<>(reservas, pageable, reservas.size());
            when(reservaRepository.findAll(any(Pageable.class))).thenReturn(page);

            // Act
            Page<ReservaEntity> result = reservaService.obterPaginados(pageable);

            // Assert
            assertEquals(page, result);
            verify(reservaRepository).findAll(any(Pageable.class));
        }

    }

    @Nested
    class ConsultarReserva {

        @Test
        void obterPorCodigo_quandoReservaExiste_deveRetornarOk() {
            // Arrange
            Long codigo = 1L;
            ReservaEntity reservaEntity = new ReservaEntity(codigo, 3L, 2L, LocalDateTime.now(), 2, 1, Status_Reserva.RESERVADO);

            when(reservaRepository.existsById(codigo)).thenReturn(true);

            when(reservaRepository.existsById(codigo)).thenReturn(true);
            when(reservaRepository.getReferenceById(codigo)).thenReturn(reservaEntity);

            // Act
            ResponseEntity<Object> response = reservaService.obterPorCodigo(codigo);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoReservaDto(reservaEntity), response.getBody());
            verify(reservaRepository).existsById(codigo);
            verify(reservaRepository).getReferenceById(codigo);
        }

        @Test
        void obterPorCodigo_quandoReservaNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long codigo = 1L;
            when(reservaRepository.existsById(codigo)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = reservaService.obterPorCodigo(codigo);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id da Reserva informado não existe!", response.getBody());
            verify(reservaRepository).existsById(codigo);
            verify(reservaRepository, never()).getReferenceById(codigo);
        }


    }
    @Nested
    class LiberarReserva {
        @Test
        void liberarMesas_quandoReservaCancelada_deveLiberarMesas() {
            // Arrange
            Long idRestaurante = 1L;
            Integer numeroMesas = 2;
            List<MesaEntity> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(new MesaEntity(1L, idRestaurante, "4", Status_Mesa.RESERVADA));
            mesasReservadas.add(new MesaEntity(2L, idRestaurante, "5", Status_Mesa.RESERVADA));
            mesasReservadas.add(new MesaEntity(3L, idRestaurante, "6", Status_Mesa.RESERVADA)); // Mesa extra para testar a liberação de apenas 2 mesas

            when(mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA)).thenReturn(mesasReservadas);

            // Act
            reservaService.liberarMesas(idRestaurante, Status_Reserva.CANCELADO, numeroMesas);

            // Assert
            verify(mesaRepository).findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA);
            verify(mesaRepository).saveAll(mesasReservadas);
            for (MesaEntity mesa : mesasReservadas) {
                if (mesa.getIdmesa() <= numeroMesas) {
                    assertEquals(Status_Mesa.DISPONIVEL, mesa.getStatus());
                } else {
                    assertEquals(Status_Mesa.RESERVADA, mesa.getStatus());
                }
            }
        }

        @Test
        void liberarMesas_quandoReservaFinalizada_deveMudarStatusParaOcupada() {
            // Arrange
            Long idRestaurante = 1L;
            Integer numeroMesas = 2;
            List<MesaEntity> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(new MesaEntity(1L, idRestaurante, "3", Status_Mesa.RESERVADA));
            mesasReservadas.add(new MesaEntity(2L, idRestaurante, "4", Status_Mesa.RESERVADA));

            when(mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA)).thenReturn(mesasReservadas);

            // Act
            reservaService.liberarMesas(idRestaurante, Status_Reserva.FINALIZADO, numeroMesas);

            // Assert
            verify(mesaRepository).findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA);
            verify(mesaRepository).saveAll(mesasReservadas);
            for (MesaEntity mesa : mesasReservadas) {
                assertEquals(Status_Mesa.OCUPADA, mesa.getStatus());
            }
        }

    }

}
