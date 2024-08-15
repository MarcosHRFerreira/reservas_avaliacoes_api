package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;


    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public RestauranteEntity criar(RestauranteEntity restauranteEntity) {

        return  restauranteRepository.save(restauranteEntity);
    }

    @Override
    public List<RestauranteEntity> obterTodos() {
        return this.restauranteRepository.findAll();
    }

    public Page<RestauranteEntity> listaRestaurantes(Pageable pageable) {
        Sort sort = Sort.by("nome").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.restauranteRepository.findAll(paginacao);
    }

    @Override
    public RestauranteEntity obterPorCodigo(Long codigo) {
        return this.restauranteRepository
                .findById(codigo)
                .orElseThrow(()-> new IllegalArgumentException("Restaurante não existe!"));
    }

    @Override
    public ResponseEntity<?> atualizarRestaurante(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {

        try {
            if (!restauranteRepository.existsById(dadosAtualizacaoRestauranteDto.id_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            var restaurante=restauranteRepository.getReferenceById (dadosAtualizacaoRestauranteDto.id_restaurante());
            restaurante.atualizarInformacoes(dadosAtualizacaoRestauranteDto);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaurante));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


}
