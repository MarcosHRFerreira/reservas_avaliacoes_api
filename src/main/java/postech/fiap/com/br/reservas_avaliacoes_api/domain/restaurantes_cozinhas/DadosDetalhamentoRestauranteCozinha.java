package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;

import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;

public record DadosDetalhamentoRestauranteCozinha(
         Long id_restaurante_cozinha,
         Long id_restaurante,
         Long id_cozinha) {

    public DadosDetalhamentoRestauranteCozinha(Restaurante_CozinhaEntity restaurante_cozinha){

        this(restaurante_cozinha.getId_restaurante_cozinha(),
                restaurante_cozinha.getId_restaurante(),
                restaurante_cozinha.getId_cozinha());
    }
}
