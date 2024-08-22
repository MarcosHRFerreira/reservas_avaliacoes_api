package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository, RestauranteRepository restauranteRepository, ClienteRepository clienteRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<?> cadastrar(AvaliacaoEntity avaliacaoEntity) {
        try {
            if (!clienteRepository.existsById(avaliacaoEntity.getID_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(avaliacaoEntity.getID_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (avaliacaoRepository.findByid_clienteAndid_restauranteAnddata_avaliacao(
                    avaliacaoEntity.getID_cliente(),
                    avaliacaoEntity.getID_restaurante(),
                    avaliacaoEntity.getData_avaliacao().toLocalDate())) {
                return ResponseEntity.badRequest().body("Já existe um registro com este ID do cliente e ID do restaurante e Data Avaliação.");
            }
            avaliacaoRepository.save(avaliacaoEntity);
            return ResponseEntity.ok(new DadosDetalhamentoAvalizacaoDto(avaliacaoEntity));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // log.error("Erro ao inserir registro: {}", e.getMessage());
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");
        }
   }
    @Override
    public ResponseEntity atualizar(DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto) {
        try {
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.ID_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.ID_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.ID_avaliacao())) {
                throw new ValidacaoException("Id da Avaliação informado não existe!");
            }
            var avaliacao = avaliacaoRepository.getReferenceById(dadosAtualizacaoAvalizacaoDto.ID_avaliacao());
            avaliacao.atualizarInformacoes(dadosAtualizacaoAvalizacaoDto);
            return ResponseEntity.ok(new DadosDetalhamentoAvalizacaoDto(avaliacao));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    public Page<AvaliacaoEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("data_avaliacao").descending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.avaliacaoRepository.findAll(paginacao);
    }
    @Override
    public AvaliacaoEntity obterPorCodigo(Long codigo) {
        return avaliacaoRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id da Avaliação informado não existe!"));
    }

}

