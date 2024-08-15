package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

public record DadosDetalhamentoRestauranteDto(

        Long id_restaurante,

        String nome,

        String email,

        String logradouro,

        String bairro,

        String cep,

        String complemento,

        String numero,

        String uf,

        String cidade) {

    public DadosDetalhamentoRestauranteDto(RestauranteEntity restaurante){

        this(restaurante.getId_restaurante(),
                restaurante.getNome(),
                restaurante.getEmail(),
                restaurante.getLogradouro(),
                restaurante.getBairro(),
                restaurante.getCep(),
                restaurante.getComplemento(),
                restaurante.getNumero(),
                restaurante.getUf(),
                restaurante.getCidade());

    }

}
