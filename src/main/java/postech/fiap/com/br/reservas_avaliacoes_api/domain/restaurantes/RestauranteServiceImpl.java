package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

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
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<Object> cadastrar(RestauranteEntity restauranteEntity) {
        try {
            if (restauranteRepository.existsBynomeAndEmail(restauranteEntity.getNome(),restauranteEntity.getEmail())) {
                throw new ValidacaoException("Já existe o Nome do Restaurante com o email na base");
            }
            if (restauranteRepository.existsByEmail (restauranteEntity.getEmail()))
            {
                throw new ValidacaoException("Já existe o email " + restauranteEntity.getEmail() + " na base de restaurantes");
            }
            var restaturante=restauranteRepository.save(restauranteEntity);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaturante));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar restaurante: " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {
        try {
            if (!restauranteRepository.existsById(dadosAtualizacaoRestauranteDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            var restaurante=restauranteRepository.getReferenceById (dadosAtualizacaoRestauranteDto.idrestaurante());
            restaurante.atualizarInformacoes(dadosAtualizacaoRestauranteDto);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaurante));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
       public Page<RestauranteEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("nome").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.restauranteRepository.findAll(paginacao);
    }
    @Override
    public RestauranteEntity obterPorCodigo(Long codigo) {
        return restauranteRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id do Restaurante informado não existe!"));
    }
}
