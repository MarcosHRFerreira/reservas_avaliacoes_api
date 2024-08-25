package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "restaurantes_cozinhas")
@Entity(name = "Restaurantes_CozinhasEntity")

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Restaurante_CozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_restaurante_cozinha;

    @NotNull
    @Min(value = 1, message = "id_restaurante deve ser maior ou igual a 1")
    private Long id_restaurante;

    @NotNull
    @Min(value = 1, message = "id_cozinha deve ser maior ou igual a 1")
    private Long id_cozinha;

    @ManyToMany
    @JoinTable(
            name = "Restaurantes_Cozinhas",
            joinColumns = @JoinColumn(name = "id_restaurante"),
            inverseJoinColumns = @JoinColumn(name = "id_cozinha")
    )

    public void atualizarInformacoes(DadosAtualizacaoRestauranteCozinhaDto dados) {

        if (dados.id_restaurante() != null) {
            this.id_restaurante = dados.id_restaurante();
        }
        if (dados.id_cozinha() != null) {
            this.id_cozinha = dados.id_cozinha();
        }

    }
}
