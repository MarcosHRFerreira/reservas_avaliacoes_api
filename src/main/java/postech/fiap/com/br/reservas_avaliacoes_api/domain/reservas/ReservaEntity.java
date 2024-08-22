package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.DadosAtualizacaoAvaliacaoDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reservas")
@Entity(name = "ReservaEntity")
@EqualsAndHashCode(of = "id")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reserva;

    private Long id_cliente;
    private Long id_restaurante;
    private LocalDateTime data_hora;
    private Integer numero_pessoas;
    private String numero_mesa;

    public void atualizarInformacoes(DadosAtualizacaoReservaDto dados) {

        if (dados.numero_pessoas() != null) {
            this.numero_pessoas = dados.numero_pessoas();
        }
        if (dados.data_hora() != null) {
            this.data_hora = dados.data_hora();
        }
        if (dados.numero_mesa() != null) {
            this.numero_mesa = dados.numero_mesa();
        }
    }
}
