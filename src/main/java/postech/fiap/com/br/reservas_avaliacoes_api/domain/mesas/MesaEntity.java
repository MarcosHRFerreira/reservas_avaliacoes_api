package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.List;

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

    @NotNull
    @Min(value = 1, message = "id_restaurante deve ser maior ou igual a 1")
    private Long id_restaurante;

    @NotBlank(message = "O número da mesa é obrigatório")
    @Size(min = 1, max=3, message = "O número da mesa deve ter no mínimo 1 caracteres e no maxímo 3")
    private String numero;

   // @NotBlank(message = "O status da mesa é obrigatório, sendo:  OCUPADA, LIVRE ou RESERVADA")
  //  @Size(min = 1, max=20, message = "O status da mesa deve ter no mínimo 1 caracteres e no maxímo 20")
    @Enumerated(EnumType.STRING)
    private Status_Mesa status ;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reserva_mesa",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "mesa_id"))



    public void atualizarInformacoes(DadosAtualizacaoMesaDto dados) {

        if (dados.status() != null) {
            this.status = dados.status();
        }

    }
}
