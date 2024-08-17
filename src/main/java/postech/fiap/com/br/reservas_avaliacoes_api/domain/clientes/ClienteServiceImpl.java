package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ClienteEntity criar(ClienteEntity clienteEntity) {
        return  clienteRepository.save(clienteEntity);
    }

    @Override
    public List<ClienteEntity> obterTodos() {
        return this.clienteRepository.findAll();
    }

    public Page<ClienteEntity> listaClientes(Pageable pageable) {
        Sort sort = Sort.by("nome").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.clienteRepository.findAll(paginacao);
    }

    @Override
    public ClienteEntity obterPorCodigo(Long codigo) {
        return this.clienteRepository
                .findById(codigo)
                .orElseThrow(()-> new IllegalArgumentException("Cliente não existe!"));
    }

    @Override
    @Transactional
    public ResponseEntity atualizarCliente(DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto) {

        try {
            if (!clienteRepository.existsById(dadosAtualizacaoClienteDto.id_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            var cliente=clienteRepository.getReferenceById (dadosAtualizacaoClienteDto.id_cliente());
            cliente.atualizarInformacoes(dadosAtualizacaoClienteDto);
            return ResponseEntity.ok(new DadosDetalhamentoClienteDto(cliente));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
