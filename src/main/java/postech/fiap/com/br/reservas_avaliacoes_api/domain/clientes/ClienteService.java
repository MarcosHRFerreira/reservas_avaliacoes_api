package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {

    public ResponseEntity<?> cadastrar(ClienteEntity clienteEntity);

    public Page<ClienteEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public ClienteEntity obterPorCodigo(Long codigo);

    public ResponseEntity atualizar(DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto);

}
