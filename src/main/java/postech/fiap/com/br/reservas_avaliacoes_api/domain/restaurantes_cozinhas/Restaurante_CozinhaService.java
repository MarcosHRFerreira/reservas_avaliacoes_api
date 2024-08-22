package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.DadosAtualizacaoRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;

@Service
public interface Restaurante_CozinhaService {

    public ResponseEntity<?> cadastrar(Restaurante_CozinhaEntity restaurante_cozinhaEntity);

    public ResponseEntity<?> atualizar(DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto);

    public Restaurante_CozinhaEntity obterPorCodigo(Long codigo);

    public Page<Restaurante_CozinhaEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

}
