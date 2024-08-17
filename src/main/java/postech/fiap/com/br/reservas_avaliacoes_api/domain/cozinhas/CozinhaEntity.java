package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.DadosAtualizacaoClienteDto;

@Table(name = "cozinhas")
@Entity(name = "CozinhaEntity")

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class CozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_cozinha;

    private String especialidade;

    public void atualizarInformacoes(DadosAtualizacaoCozinhaDto dados) {

        if (dados.especialidade() != null) {
            this.especialidade = dados.especialidade().toUpperCase();
        }
    }
}
