package postech.fiap.com.br.reservas_avaliacoes_api.service.clientes;

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
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    ClienteServiceImpl clienteService;


    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl( clienteRepository);
    }
    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Nested
    class CadastrarCliente{

        @Test
        void cadastrar_quandoClienteNaoExiste_deveRetornarOk() {
            // Arrange
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

            when(clienteRepository.existsBynomeAndEmail(clienteEntity.getNome(), clienteEntity.getEmail())).thenReturn(false);
            when(clienteRepository.existsByEmail(clienteEntity.getEmail())).thenReturn(false);
            when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);

            // Act
            ResponseEntity<Object> response = clienteService.cadastrar(clienteEntity);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoClienteDto(clienteEntity), response.getBody());
            verify(clienteRepository).existsBynomeAndEmail(clienteEntity.getNome(), clienteEntity.getEmail());
            verify(clienteRepository).existsByEmail(clienteEntity.getEmail());
            verify(clienteRepository).save(clienteEntity);
        }

        @Test
        void cadastrar_quandoClienteJaExisteComNomeEEmail_deveRetornarBadRequest() {
            // Arrange

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

            when(clienteRepository.existsBynomeAndEmail(clienteEntity.getNome(), clienteEntity.getEmail())).thenReturn(true);

            // Act
            ResponseEntity<Object> response = clienteService.cadastrar(clienteEntity);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Erro ao cadastrar: Já existe o Nome e o email na base", response.getBody());
            verify(clienteRepository, never()).save(clienteEntity);
        }

        @Test
        void cadastrar_quandoClienteJaExisteComEmail_deveRetornarBadRequest() {
            // Arrange
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

            when(clienteRepository.existsBynomeAndEmail(clienteEntity.getNome(), clienteEntity.getEmail())).thenReturn(false);
            when(clienteRepository.existsByEmail(clienteEntity.getEmail())).thenReturn(true);

            // Act
            ResponseEntity<Object> response = clienteService.cadastrar(clienteEntity);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Erro ao cadastrar: Já existe o email ze@teste.com.br na base de clientes", response.getBody());
            verify(clienteRepository, never()).save(clienteEntity);
        }

    }
    @Nested
    class AtualizarCliente {

        @Test
        void atualizar_quandoClienteExiste_deveRetornarOk() {
            // Arrange
            Long idCliente = 1L;
            DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto = new DadosAtualizacaoClienteDto(1L,"Zeee","ze@teddste.com.br","rua do ze","bairro do ze","12345678","SP","Cidade do Ze","2","SP","1234567890");

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

            when(clienteRepository.existsById(idCliente)).thenReturn(true);
            when(clienteRepository.getReferenceById(idCliente)).thenReturn(clienteEntity);

            // Act
            ResponseEntity<Object> response = clienteService.atualizar(dadosAtualizacaoClienteDto);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoClienteDto(clienteEntity), response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(clienteRepository).getReferenceById(idCliente);

        }

        @Test
        void atualizar_quandoClienteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long idCliente = 1L;
            DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto = new DadosAtualizacaoClienteDto(1L,"Zeee","ze@teddste.com.br","rua do ze","bairro do ze","12345678","SP","Cidade do Ze","2","SP","1234567890");
            when(clienteRepository.existsById(idCliente)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = clienteService.atualizar(dadosAtualizacaoClienteDto);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Id do Cliente informado não existe!", response.getBody());
            verify(clienteRepository).existsById(idCliente);
            verify(clienteRepository, never()).getReferenceById(idCliente);
        }

    }
    @Nested
    class PaginarCliente {

        @Test
        void obterPaginados_quandoPaginacaoValida_deveRetornarPaginaDeClientes() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
            List<ClienteEntity> clientes = new ArrayList<>();
            clientes.add(new ClienteEntity(1L,"Zeee","ze@teddste.com.br","rua do ze","bairro do ze","12345678","SP","Cidade do Ze","2","SP","1234567890"));
            clientes.add(new ClienteEntity(2L,"joao","joao@teddste.com.br","rua do joao","bairro do joao","12345678","SP","Cidade do joao","22","SP","1234567890"));

            Page<ClienteEntity> page = new org.springframework.data.domain.PageImpl<>(clientes, pageable, clientes.size());
            when(clienteRepository.findAll(any(Pageable.class))).thenReturn(page);

            // Act
            Page<ClienteEntity> result = clienteService.obterPaginados(pageable);

            // Assert
            assertEquals(page, result);
            verify(clienteRepository).findAll(any(Pageable.class));
        }

    }
    @Nested
    class ConsultarCodigoCliente {

        @Test
        void obterPorCodigo_quandoClienteExiste_deveRetornarOk() {
            // Arrange
            Long codigo = 1L;

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

            when(clienteRepository.existsById(codigo)).thenReturn(true);
            when(clienteRepository.getReferenceById(codigo)).thenReturn(clienteEntity);

            // Act
            ResponseEntity<Object> response = clienteService.obterPorCodigo(codigo);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(new DadosDetalhamentoClienteDto(clienteEntity), response.getBody());
            verify(clienteRepository).existsById(codigo);
            verify(clienteRepository).getReferenceById(codigo);
        }

        @Test
        void obterPorCodigo_quandoClienteNaoExiste_deveRetornarNotFound() {
            // Arrange
            Long codigo = 1L;
            when(clienteRepository.existsById(codigo)).thenReturn(false);

            // Act
            ResponseEntity<Object> response = clienteService.obterPorCodigo(codigo);

            // Assert
             assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
             assertEquals("Id do Cliente informado não existe!", response.getBody());
             verify(clienteRepository).existsById(codigo);
             verify(clienteRepository, never()).getReferenceById(codigo);
        }

    }

 }
