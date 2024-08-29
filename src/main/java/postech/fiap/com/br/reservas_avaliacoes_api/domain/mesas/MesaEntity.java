package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor

@Table(name="mesas")
@Entity(name = "MesaEntity")

public class MesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmesa;

    @NotNull
    @Min(value = 1, message = "idrestaurante deve ser maior ou igual a 1")
    private Long idrestaurante;

    @NotBlank(message = "O número da mesa é obrigatório")
    @Size(min = 1, max=3, message = "O número da mesa deve ter no mínimo 1 caracteres e no maxímo 3")
    private String numero;

    @Enumerated(EnumType.STRING)
    private Status_Mesa status ;

    public MesaEntity() {
        //
    }
    public Long getIdmesa() {
        return idmesa;
    }
    public void setIdmesa(Long idmesa) {
        this.idmesa = idmesa;
    }
    public Long getIdrestaurante() {
        return idrestaurante;
    }
    public void setIdrestaurante(Long idrestaurante) {
        this.idrestaurante = idrestaurante;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Status_Mesa getStatus() {
        return status;
    }
    public void setStatus(Status_Mesa status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MesaEntity{" +
                "idmesa=" + idmesa +
                ", idrestaurante=" + idrestaurante +
                ", numero='" + numero + '\'' +
                ", status=" + status +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MesaEntity that = (MesaEntity) o;
        return Objects.equals(idmesa, that.idmesa) && Objects.equals(idrestaurante, that.idrestaurante) && Objects.equals(numero, that.numero) && status == that.status;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(idmesa);
    }
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
