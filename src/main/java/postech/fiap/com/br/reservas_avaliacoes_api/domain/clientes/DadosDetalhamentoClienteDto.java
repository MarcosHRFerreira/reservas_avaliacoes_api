package postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes;

import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;

public record DadosDetalhamentoClienteDto(

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

        String cidade) {

    public DadosDetalhamentoClienteDto(ClienteEntity cliente){

        this(cliente.getId_cliente(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getLogradouro(),
                cliente.getBairro(),
                cliente.getCep(),
                cliente.getComplemento(),
                cliente.getNumero(),
                cliente.getUf(),
                cliente.getCidade());

    }

}
