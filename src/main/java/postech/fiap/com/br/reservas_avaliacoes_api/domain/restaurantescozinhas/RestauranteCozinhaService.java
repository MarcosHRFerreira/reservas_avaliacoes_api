package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RestauranteCozinhaService {

    public ResponseEntity<Object> cadastrar(RestauranteCozinhaEntity restaurantecozinhaEntity);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto);

    public ResponseEntity obterPorCodigo(Long codigo);

    public Page<RestauranteCozinhaEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

}
