package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.DadosAtualizacaoAvaliacaoDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.Status_Mesa;

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

    @NotNull
    @Min(value = 1, message = "id_cliente deve ser maior ou igual a 1")
    private Long id_cliente;

    @NotNull
    @Min(value = 1, message = "id_restaurante deve ser maior ou igual a 1")
    private Long id_restaurante;

  //  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "A data e hora devem estar no formato yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime data_hora;

    @NotNull
    @Min(value = 1, message = "numero_pessoas deve ser maior ou igual a 1")
    @Max(value = 1000, message = "numero_pessoas deve ser maior ou igual a 1000")
    private Integer numero_pessoas;

    @NotNull
    @Min(value = 1, message = "Número de mesas deve ser maior ou igual a 1")
    @Max(value = 100, message = "Número de mesas deve ser menor ou igual a 100")
    private Integer numero_mesas;

  //  @NotBlank(message = "O status da reserva é obrigatório, sendo:  RESERVADO, CANCELADO ou FINALIZADO")
  //  @Size(min = 1, max=20, message = "O status da mesa deve ter no mínimo 1 caracteres e no maxímo 20")
    @Enumerated(EnumType.STRING)
    private Status_Reserva status ;


    public void atualizarInformacoes(DadosAtualizacaoReservaDto dados) {

        if (dados.numero_pessoas() != null) {
            this.numero_pessoas = dados.numero_pessoas();
        }
        if (dados.data_hora() != null) {
            this.data_hora = dados.data_hora();
        }
        if (dados.numero_mesas() != null) {
            this.numero_mesas = dados.numero_mesas();
        }
        if (dados.status()!= null){
            this.status =dados.status();
        }
    }
}
