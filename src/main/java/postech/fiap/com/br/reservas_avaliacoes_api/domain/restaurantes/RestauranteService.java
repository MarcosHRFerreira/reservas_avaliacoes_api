package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

public interface RestauranteService {

    public ResponseEntity<Object> cadastrar(RestauranteEntity restauranteEntity);

    public Page<RestauranteEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public ResponseEntity<Object> obterPorCodigo(Long codigo);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto);

    public ResponseEntity<Object> obterPorNome(String nome);

    public ResponseEntity<Object> obterPorUF(String uf);

    public ResponseEntity<Object> obterRestaurantesPorCozinha(String codcozinha);

}
