package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@Service
public class RestauranteCozinhaServiceImpl implements RestauranteCozinhaService {

    private final RestauranteCozinhaRepository restaurantecozinhaRepository;
    private final CozinhaRepository cozinhaRepository;
    private final RestauranteRepository restauranteRepository;

    public RestauranteCozinhaServiceImpl(RestauranteCozinhaRepository restauranteCozinhaRepository, CozinhaRepository cozinhaRepository, RestauranteRepository restauranteRepository) {
        restaurantecozinhaRepository = restauranteCozinhaRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<Object> cadastrar(RestauranteCozinhaEntity restaurantecozinhaEntity) {
        try {
            if (!restauranteRepository.existsById(restaurantecozinhaEntity.getIdrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!cozinhaRepository.existsById(restaurantecozinhaEntity.getIdcozinha())) {
                throw new ValidacaoException("Id da Cozinha informado não existe!");
            }
            if (restaurantecozinhaRepository.findByidrestauranteAndidcozinha(
                    restaurantecozinhaEntity.getIdrestaurante(),
                    restaurantecozinhaEntity.getIdcozinha())) {
                return ResponseEntity.badRequest().body("Já existe um registro com este ID de restaurante e ID de cozinha.");
            }
            restaurantecozinhaRepository.save(restaurantecozinhaEntity);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteCozinha(restaurantecozinhaEntity));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto) {
        try {
            if (!restaurantecozinhaRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.idrestaurante_cozinha())) {
                throw new ValidacaoException("Id do Restaurante_Cozinha informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!cozinhaRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.idcozinha())) {
                throw new ValidacaoException("Id da Cozinha informado não existe!");
            }
            var restaurantecozinha = restaurantecozinhaRepository.getReferenceById(dadosAtualizacaoRestauranteCozinhaDto.idrestaurante_cozinha());
            restaurantecozinha.atualizarInformacoes(dadosAtualizacaoRestauranteCozinhaDto);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteCozinha(restaurantecozinha));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    public RestauranteCozinhaEntity obterPorCodigo(Long codigo) {
        return restaurantecozinhaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id do Restaurante_Cozinha informado não existe!"));
    }
    @Override
    public Page<RestauranteCozinhaEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("id_restaurante_cozinha").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.restaurantecozinhaRepository.findAll(paginacao);
    }

}
