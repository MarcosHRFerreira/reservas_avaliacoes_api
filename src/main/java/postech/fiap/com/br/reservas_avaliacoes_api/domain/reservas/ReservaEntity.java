package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

@Table(name="reservas")
@Entity(name = "ReservaEntity")

public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreserva;

    @NotNull
    @Min(value = 1, message = "id_cliente deve ser maior ou igual a 1")
    private Long idcliente;

    @NotNull
    @Min(value = 1, message = "id_restaurante deve ser maior ou igual a 1")
    private Long idrestaurante;

  //  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "A data e hora devem estar no formato yyyy-MM-ddTHH:mm:ss")
  @NotNull
    private LocalDateTime datahora;

    @NotNull
    @Min(value = 1, message = "numero_pessoas deve ser maior ou igual a 1")
    @Max(value = 1000, message = "numero_pessoas deve ser maior ou igual a 1000")
    private Integer numeropessoas;

    @NotNull
    @Min(value = 1, message = "Número de mesas deve ser maior ou igual a 1")
    @Max(value = 100, message = "Número de mesas deve ser menor ou igual a 100")
    private Integer numeromesas;

  //  @NotBlank(message = "O status da reserva é obrigatório, sendo:  RESERVADO, CANCELADO ou FINALIZADO")
  //  @Size(min = 1, max=20, message = "O status da mesa deve ter no mínimo 1 caracteres e no maxímo 20")
    @Enumerated(EnumType.STRING)
    private Status_Reserva status ;


    public Long getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Long idreserva) {
        this.idreserva = idreserva;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public Long getIdrestaurante() {
        return idrestaurante;
    }

    public void setIdrestaurante(Long idrestaurante) {
        this.idrestaurante = idrestaurante;
    }

    public LocalDateTime getDatahora() {
        return datahora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
    }

    public Integer getNumeropessoas() {
        return numeropessoas;
    }

    public void setNumeropessoas(Integer numeropessoas) {
        this.numeropessoas = numeropessoas;
    }

    public Integer getNumeromesas() {
        return numeromesas;
    }

    public void setNumeromesas(Integer numeromesas) {
        this.numeromesas = numeromesas;
    }

    public Status_Reserva getStatus() {
        return status;
    }

    public void setStatus(Status_Reserva status) {
        this.status = status;
    }

    public ReservaEntity() {
        //
    }

    @Override
    public String toString() {
        return "ReservaEntity{" +
                "idreserva=" + idreserva +
                ", idcliente=" + idcliente +
                ", idrestaurante=" + idrestaurante +
                ", datahora=" + datahora +
                ", numeropessoas=" + numeropessoas +
                ", numeromesas=" + numeromesas +
                ", status=" + status +
                '}';
    }

    public void atualizarInformacoes(DadosAtualizacaoReservaDto dados) {

        if (dados.numeropessoas() != null) {
            this.numeropessoas = dados.numeropessoas();
        }
        if (dados.datahora() != null) {
            this.datahora = dados.datahora();
        }
        if (dados.numeropessoas() != null) {
            this.numeromesas = dados.numeromesas();
        }
        if (dados.status()!= null){
            this.status =dados.status();
        }
    }




}
