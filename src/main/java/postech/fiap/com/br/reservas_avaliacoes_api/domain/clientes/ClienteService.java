package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {

    public ClienteEntity criar(ClienteEntity clienteEntity);

    public List<ClienteEntity> obterTodos();

    public Page<ClienteEntity> listaClientes(@PageableDefault(size = 10) Pageable pageable);

    public ClienteEntity obterPorCodigo(Long codigo);

    public ResponseEntity atualizarCliente(DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto);

}
