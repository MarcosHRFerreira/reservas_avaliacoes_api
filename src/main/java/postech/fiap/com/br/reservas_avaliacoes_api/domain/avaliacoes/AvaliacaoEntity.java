package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "avaliacoes")
@Entity(name = "AvaliacaoEntity")

public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idavaliacao;

    @NotNull
    @Min(value = 1, message = "idcliente deve ser maior ou igual a 1")
    private Long idcliente;

    @NotNull
    @Min(value = 1, message = "idrestaurante deve ser maior ou igual a 1")
    private Long idrestaurante;

    @NotNull
    @Min(value = 1, message = "avaliacao deve ser no mínimo 1")
    @Max(value = 10, message = "avaliacao deve no máximo 10")
    private Long avaliacao;

    @NotNull
    @NotBlank(message = "O comentário é obrigatório")
    @Size(min = 1, max = 200, message = "O comentário deve ter no mínimo 1 caracteres e no maxímo 200")
    private String comentario;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataavaliacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoEntity that = (AvaliacaoEntity) o;
        return Objects.equals(idavaliacao, that.idavaliacao);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(idavaliacao);
    }
    @Override
    public String toString() {
        return "AvaliacaoEntity{" +
                "idcliente=" + idcliente +
                ", idrestaurante=" + idrestaurante +
                ", avaliacao=" + avaliacao +
                ", comentario='" + comentario + '\'' +
                ", dataavaliacao=" + dataavaliacao +
                '}';
    }

    public Long getidavaliacao() {
        return idavaliacao;
    }
    public void setidavaliacao(Long idavaliacao) {
        this.idavaliacao = idavaliacao;
    }
    public Long getidcliente() {
        return idcliente;
    }
    public void setidcliente(Long idcliente) {
        this.idcliente = idcliente;
    }
    public Long getidrestaurante() {
        return idrestaurante;
    }
    public void setidrestaurante(Long idrestaurante) {
        this.idrestaurante = idrestaurante;
    }
    public Long getavaliacao() {
        return avaliacao;
    }
    public void setavaliacao(Long avaliacao) {
        this.avaliacao = avaliacao;
    }
    public String getcomentario() {
        return comentario;
    }
    public void setcomentario(String comentario) {
        this.comentario = comentario;
    }
    public LocalDateTime getdataavaliacao() {
        return dataavaliacao;
    }
    public void setdataavaliacao(LocalDateTime dataavaliacao) {
        this.dataavaliacao = dataavaliacao;
    }
    public void atualizarInformacoes(DadosAtualizacaoAvaliacaoDto dados) {

        if (dados.avaliacao() != null) {
            this.avaliacao = dados.avaliacao();
        }
        if (dados.comentario() != null) {
            this.comentario = dados.comentario().toUpperCase();
        }
        if (dados.dataavaliacao() != null) {
            this.dataavaliacao = dados.dataavaliacao();
        }
    }
}
