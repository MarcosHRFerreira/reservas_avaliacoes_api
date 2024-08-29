package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;


public interface ClienteService {

    public ResponseEntity<Object> cadastrar(ClienteEntity clienteEntity);

    public Page<ClienteEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public ClienteEntity obterPorCodigo(Long codigo);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto);

}
