package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "A especialidade é obrigatória")
    @Size(min = 1, max=200, message = "O nome do bairro deve ter no mínimo 3 caracteres e no maxímo 200")
    private String especialidade;

    public void atualizarInformacoes(DadosAtualizacaoCozinhaDto dados) {

        if (dados.especialidade() != null) {
            this.especialidade = dados.especialidade().toUpperCase();
        }
    }

}
