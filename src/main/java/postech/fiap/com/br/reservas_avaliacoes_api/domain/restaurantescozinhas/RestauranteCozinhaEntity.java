package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "restaurantes_cozinhas")
@Entity(name = "RestaurantesCozinhasEntity")

@Getter
@Setter
@AllArgsConstructor

public class RestauranteCozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idrestaurantecozinha;

    @NotNull
    @Min(value = 1, message = "idrestaurante deve ser maior ou igual a 1")
    private Long idrestaurante;

    @NotNull
    @Min(value = 1, message = "id_cozinha deve ser maior ou igual a 1")
    private Long idcozinha;

    @ManyToMany
    @JoinTable(
            name = "Restaurantes_Cozinhas",
            joinColumns = @JoinColumn(name = "idrestaurante"),
            inverseJoinColumns = @JoinColumn(name = "idcozinha")
    )


    public Long getIdrestaurantecozinha() {
        return idrestaurantecozinha;
    }

    public void setIdrestaurantecozinha(Long idrestaurantecozinha) {
        this.idrestaurantecozinha = idrestaurantecozinha;
    }

    public Long getIdrestaurante() {
        return idrestaurante;
    }

    public void setIdrestaurante(Long idrestaurante) {
        this.idrestaurante = idrestaurante;
    }

    public Long getIdcozinha() {
        return idcozinha;
    }

    public void setIdcozinha(Long idcozinha) {
        this.idcozinha = idcozinha;
    }

    @Override
    public String toString() {
        return "Restaurante_CozinhaEntity{" +
                "idrestaurantecozinha=" + idrestaurantecozinha +
                ", idrestaurante=" + idrestaurante +
                ", idcozinha=" + idcozinha +
                '}';
    }

    public RestauranteCozinhaEntity() {
        //
    }

    public void atualizarInformacoes(DadosAtualizacaoRestauranteCozinhaDto dados) {

        if (dados.idrestaurante() != null) {
            this.idrestaurante = dados.idrestaurante();
        }
        if (dados.idcozinha() != null) {
            this.idcozinha = dados.idcozinha();
        }

    }
}
