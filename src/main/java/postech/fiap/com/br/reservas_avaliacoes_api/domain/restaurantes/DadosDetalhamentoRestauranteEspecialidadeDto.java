package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DadosDetalhamentoRestauranteEspecialidadeDto {
   private Long idrestaurante;
    private String nome;
    private String email;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String cep;
    private String complemento;
    private String numero;
    private String uf;
    private  String cidade;
    private String capacidade;
    private String funcionamento;
    private String especialidade;
}

