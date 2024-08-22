package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mesas")
@Entity(name = "MesaEntity")
@EqualsAndHashCode(of = "id")

public class MesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mesa;
    private Long id_restaurante;
    private String numero;
    @Enumerated(EnumType.STRING)
    private Status_Mesa status;


    public void atualizarInformacoes(DadosAtualizacaoMesaDto dados) {

        if (dados.status() != null) {
            this.status = dados.status();
        }

    }
}
