package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@Service
public class Restaurante_CozinhaServiceImpl implements Restaurante_CozinhaService {

    private final Restaurante_CozinhaRepository restaurante_cozinhaRepository;
    private final CozinhaRepository cozinhaRepository;
    private final RestauranteRepository restauranteRepository;

    public Restaurante_CozinhaServiceImpl(Restaurante_CozinhaRepository restauranteCozinhaRepository, CozinhaRepository cozinhaRepository, RestauranteRepository restauranteRepository) {
        restaurante_cozinhaRepository = restauranteCozinhaRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<?> cadastrar(Restaurante_CozinhaEntity restaurante_cozinhaEntity) {
        try {
            if (!restauranteRepository.existsById(restaurante_cozinhaEntity.getId_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!cozinhaRepository.existsById(restaurante_cozinhaEntity.getId_cozinha())) {
                throw new ValidacaoException("Id da Cozinha informado não existe!");
            }
            if (restaurante_cozinhaRepository.findByid_restauranteAndid_cozinha(
                    restaurante_cozinhaEntity.getId_restaurante(),
                    restaurante_cozinhaEntity.getId_cozinha())) {
                return ResponseEntity.badRequest().body("Já existe um registro com este ID de restaurante e ID de cozinha.");
            }
            restaurante_cozinhaRepository.save(restaurante_cozinhaEntity);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteCozinha(restaurante_cozinhaEntity));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
           // log.error("Erro ao inserir registro: {}", e.getMessage());
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");
        }
    }
    @Override
    @Transactional
    public ResponseEntity<?> atualizar(DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto) {
        try {
            if (!restaurante_cozinhaRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.id_restaurante_cozinha())) {
                throw new ValidacaoException("Id do Restaurante_Cozinha informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.id_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!cozinhaRepository.existsById(dadosAtualizacaoRestauranteCozinhaDto.id_cozinha())) {
                throw new ValidacaoException("Id da Cozinha informado não existe!");
            }
            var restaurante_cozinha = restaurante_cozinhaRepository.getReferenceById(dadosAtualizacaoRestauranteCozinhaDto.id_restaurante_cozinha());
            restaurante_cozinha.atualizarInformacoes(dadosAtualizacaoRestauranteCozinhaDto);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteCozinha(restaurante_cozinha));
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    public Restaurante_CozinhaEntity obterPorCodigo(Long codigo) {
        return restaurante_cozinhaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id do Restaurante_Cozinha informado não existe!"));
    }
    @Override
    public Page<Restaurante_CozinhaEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("id_restaurante_cozinha").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.restaurante_cozinhaRepository.findAll(paginacao);
    }

}
