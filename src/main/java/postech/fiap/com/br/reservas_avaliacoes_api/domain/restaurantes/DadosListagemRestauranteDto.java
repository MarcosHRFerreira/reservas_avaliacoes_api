package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

public record DadosListagemRestauranteDto(

        Long idrestaurante,

        String nome,

        String email,

        String logradouro,

        String bairro,

        String cep,

        String complemento,

        String numero,

        String uf,

        String cidade


) {
}
