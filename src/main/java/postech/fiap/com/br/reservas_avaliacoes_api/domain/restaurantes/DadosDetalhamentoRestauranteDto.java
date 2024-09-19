package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

public record DadosDetalhamentoRestauranteDto(
        Long idrestaurante,
        String nome,
        String email,
        String telefone,
        String logradouro,
        String bairro,
        String cep,
        String complemento,
        String numero,
        String UF,
        String cidade,
        String capacidade,
        String funcionamento
) {

    public DadosDetalhamentoRestauranteDto(RestauranteEntity restaurante){
        this(restaurante.getIdrestaurante(),
                restaurante.getNome(),
                restaurante.getEmail().trim(),
                restaurante.getTelefone(),
                restaurante.getLogradouro(),
                restaurante.getBairro(),
                restaurante.getCep(),
                restaurante.getComplemento(),
                restaurante.getNumero(),
                restaurante.getUf(),
                restaurante.getCidade(),
                restaurante.getFuncionamento(),
                restaurante.getCapacidade()
        );
    }
}
