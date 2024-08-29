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

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    @Override
    @Transactional
    public  ResponseEntity<Object> cadastrar(ClienteEntity clienteEntity) {
        try {
            if (clienteRepository.existsBynomeAndEmail(clienteEntity.getNome(), clienteEntity.getEmail())) {
                throw new ValidacaoException("Já existe o Nome e o email na base");
            }
            if (clienteRepository.existsByEmail (clienteEntity.getEmail()))
            {
                throw new ValidacaoException("Já existe o email " + clienteEntity.getEmail() + " na base de clientes");
            }
            var cliente=clienteRepository.save(clienteEntity);
            return ResponseEntity.ok(new DadosDetalhamentoClienteDto(cliente));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar: " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto) {
        try {
            if (!clienteRepository.existsById(dadosAtualizacaoClienteDto.idcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            var cliente=clienteRepository.getReferenceById (dadosAtualizacaoClienteDto.idcliente());
            cliente.atualizarInformacoes(dadosAtualizacaoClienteDto);
            return ResponseEntity.ok(new DadosDetalhamentoClienteDto(cliente));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public Page<ClienteEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("nome").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.clienteRepository.findAll(paginacao);
    }
    @Override
    public ClienteEntity obterPorCodigo(Long codigo) {
        return clienteRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id do Cliente informado não existe!"));
    }

}
