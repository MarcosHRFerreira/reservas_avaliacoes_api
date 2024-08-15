package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestauranteService {

    public RestauranteEntity criar(RestauranteEntity restauranteEntity);

    public List<RestauranteEntity> obterTodos();

    public Page<RestauranteEntity> listaRestaurantes(@PageableDefault(size = 10) Pageable pageable);

    public RestauranteEntity obterPorCodigo(Long codigo);

    public ResponseEntity<?> atualizarRestaurante(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto);

}
