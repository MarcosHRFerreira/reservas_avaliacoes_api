package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestauranteService {

    public ResponseEntity<?> cadastrar(RestauranteEntity restauranteEntity);

    public Page<RestauranteEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public RestauranteEntity obterPorCodigo(Long codigo);

    public ResponseEntity<?> atualizar(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto);

}
