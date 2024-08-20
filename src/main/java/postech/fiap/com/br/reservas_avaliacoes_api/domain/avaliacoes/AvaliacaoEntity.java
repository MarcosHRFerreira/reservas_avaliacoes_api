package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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


    private Long ID_cliente;
    private Long ID_restaurante;

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


    private Long avaliacao;

    private String comentario;

    private LocalDateTime data_avaliacao;

    public void atualizarInformacoes(DadosAtualizacaoAvaliacaoDto dados) {

//        if (dados.ID_cliente() != null) {
//            this.ID_cliente = dados.ID_cliente();
//        }
//        if (dados.ID_restaurante() != null) {
//            this.ID_restaurante = dados.ID_restaurante();
//        }
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
