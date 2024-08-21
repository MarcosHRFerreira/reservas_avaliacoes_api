package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import java.time.LocalDateTime;

public record DadosDetalhamentoReservaDto(
        Long id_reserva,
        Long id_cliente,
        Long id_restaurante,
        LocalDateTime data_hora,
        Integer numero_pessoas) {

    public DadosDetalhamentoReservaDto(ReservaEntity reserva){
        this(reserva.getId_reserva(),
                reserva.getId_cliente(),
                reserva.getId_restaurante(),
                reserva.getData_hora(),
                reserva.getNumero_pessoas());
    }
}
