package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.DadosAtualizacaoRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;

@Service
public interface Restaurante_CozinhaService {

    public ResponseEntity<?> criar(Restaurante_CozinhaEntity restaurante_cozinhaEntity);

    public ResponseEntity<?> atualizarRestaurante_Cozinha(DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto);

}
