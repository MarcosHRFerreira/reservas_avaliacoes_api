package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import java.time.LocalDateTime;

public record DadosDetalhamentoReservaDto(
        Long idreserva,
        Long idcliente,
        Long idrestaurante,
        LocalDateTime datahora,
        Integer numeropessoas,
        Integer numeromesas,
        Status_Reserva status) {

    public DadosDetalhamentoReservaDto(ReservaEntity reserva){
        this(reserva.getIdreserva(),
                reserva.getIdcliente(),
                reserva.getIdrestaurante(),
                reserva.getDatahora(),
                reserva.getNumeropessoas(),
                reserva.getNumeromesas(),
                reserva.getStatus()
               );
    }
}
