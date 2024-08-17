package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

public record DadosAtualizacaoClienteDto(
        Long id_cliente,

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
