package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;

public record DadosAtualizacaoRestauranteCozinhaDto(
        Long id_restaurante_cozinha,
        Long id_restaurante,
        Long id_cozinha
) {
}
