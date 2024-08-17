package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

public record DadosAtualizacaoRestauranteDto(
        Long id_restaurante,

        String nome,

        String email,

        String telefone,

        String logradouro,

        String bairro,

        String cep,

        String complemento,

        String numero,

        String uf,

        String cidade
) {
}
