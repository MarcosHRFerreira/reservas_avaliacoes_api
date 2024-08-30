package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ErroExclusaoException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    public ResponseEntity<Object> cadastrar(AvaliacaoEntity avaliacaoEntity) {
        try {
            if (!clienteRepository.existsById(avaliacaoEntity.getidcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(avaliacaoEntity.getidrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (avaliacaoRepository.findByid_clienteAndid_restauranteAnddata_avaliacao(
                    avaliacaoEntity.getidcliente(),
                    avaliacaoEntity.getidrestaurante(),
                    avaliacaoEntity.getdataavaliacao().toLocalDate())) {
                   throw new ValidacaoException("Já existe um registro com este ID do cliente e ID do restaurante e Data Avaliação.");
            }else {
                var avaliacao = avaliacaoRepository.save(avaliacaoEntity);
                return ResponseEntity.ok(new DadosDetalhamentoAvalizacaoDto(avaliacao));
            }
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar: " + e.getMessage());
        }
   }
    @Override
    public ResponseEntity atualizar(DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto) {
        try {
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.idcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!avaliacaoRepository.existsById(dadosAtualizacaoAvalizacaoDto.idavaliacao())) {
                throw new ValidacaoException("Id da Avaliação informado não existe!");
            }
            var avaliacao = avaliacaoRepository.getReferenceById(dadosAtualizacaoAvalizacaoDto.idavaliacao());
            avaliacao.atualizarInformacoes(dadosAtualizacaoAvalizacaoDto);
            return ResponseEntity.ok(new DadosDetalhamentoAvalizacaoDto(avaliacao));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> excluirAvaliacao(Long codigo) {
          try {
              avaliacaoRepository.deleteById(codigo);
              return ResponseEntity.noContent().build();
          }catch (ErroExclusaoException e){
              return ResponseEntity.badRequest().build();
          }
    }

    @Override
    public Page<AvaliacaoEntity> obterPaginados(Pageable pageable) {
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize());
        return this.avaliacaoRepository.findAll(paginacao);
    }
    @Override
    public AvaliacaoEntity obterPorCodigo(Long codigo) {
        return avaliacaoRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id da Avaliação informado não existe!"));
    }

    public ResponseEntity<List<EstatisticaRestauranteDto>> getEstatisticasRestauranteUltimos30Dias(Long idRestaurante) {
        List<Object[]> resultados = idRestaurante != null ?
                avaliacaoRepository.getEstatisticasRestauranteUltimos30Dias(idRestaurante) :
                avaliacaoRepository.getEstatisticasRestauranteUltimos30DiasTodos();
        return processarResultados(resultados);
    }

    public ResponseEntity<List<EstatisticaRestauranteDto>> getEstatisticasRestauranteUltimos30DiasTodos() {
        return getEstatisticasRestauranteUltimos30Dias(null);
    }

    private ResponseEntity<List<EstatisticaRestauranteDto>> processarResultados(List<Object[]> resultados) {
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<EstatisticaRestauranteDto> estatisticas = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nome = (String) resultado[0];
            Long totalAvaliacoes = (Long) resultado[1];

            BigDecimal mediaAvaliacaoBigDecimal = (BigDecimal) resultado[2];
            mediaAvaliacaoBigDecimal = mediaAvaliacaoBigDecimal.setScale(2, RoundingMode.HALF_UP);
            Double mediaAvaliacao = mediaAvaliacaoBigDecimal.doubleValue();
            EstatisticaRestauranteDto estatistica = new EstatisticaRestauranteDto(nome, totalAvaliacoes, mediaAvaliacao);
            estatisticas.add(estatistica);
        }
        return ResponseEntity.ok(estatisticas);
    }
}

