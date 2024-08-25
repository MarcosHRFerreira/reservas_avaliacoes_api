package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Table(name = "avaliacoes")
@Entity(name = "AvaliacaoEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_avaliacao;


//    @ManyToOne
//    @JoinColumn(name = "ID_cliente")
//    private ClienteEntity clienteEntity;
//
////    @Column(insertable=false, updatable=false)
////    private Long ID_cliente;
//
//    @ManyToOne
//    @JoinColumn(name = "ID_restaurante")
//    private RestauranteEntity restauranteEntity;
//
////    @Column(insertable=false, updatable=false)
////    private Long ID_restaurante;


    @NotNull
    @Min(value = 1, message = "ID_cliente deve ser maior ou igual a 1")
    private Long ID_cliente;

    @NotNull
    @Min(value = 1, message = "ID_restaurante deve ser maior ou igual a 1")
    private Long ID_restaurante;

    @NotNull
    @Min(value = 1, message = "Avaliacao deve ser no mínimo 1")
    @Max(value = 10, message = "Avaliacao deve no máximo 10")
    private Long avaliacao;

    @NotBlank(message = "O comentário é obrigatório")
    @Size(min = 1, max=200, message = "O comentário deve ter no mínimo 1 caracteres e no maxímo 200")
    private String comentario;

    @DateTimeFormat
    private LocalDateTime data_avaliacao;

    public void atualizarInformacoes(DadosAtualizacaoAvaliacaoDto dados) {

        if (dados.avaliacao() != null) {
            this.avaliacao = dados.avaliacao();
        }
        if (dados.comentario() != null) {
            this.comentario = dados.comentario().toUpperCase();
        }
        if (dados.data_avaliacao() != null) {
            this.data_avaliacao = dados.data_avaliacao();
        }
    }
}
