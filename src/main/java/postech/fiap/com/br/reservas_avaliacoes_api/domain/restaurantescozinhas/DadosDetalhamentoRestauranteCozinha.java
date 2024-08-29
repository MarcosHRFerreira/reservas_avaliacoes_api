package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas;

public record DadosDetalhamentoRestauranteCozinha(
         Long idrestaurante_cozinha,
         Long idrestaurante,
         Long idcozinha) {

    public DadosDetalhamentoRestauranteCozinha(RestauranteCozinhaEntity restaurantecozinha){

        this(restaurantecozinha.getIdrestaurantecozinha(),
                restaurantecozinha.getIdrestaurante(),
                restaurantecozinha.getIdcozinha());
    }
}
