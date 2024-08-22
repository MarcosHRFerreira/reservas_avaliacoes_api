package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import java.time.LocalDateTime;

public record DadosAtualizacaoReservaDto(
        Long id_reserva,
        Long id_cliente,
        Long id_restaurante,
        LocalDateTime data_hora,
        Integer numero_pessoas,
        String numero_mesa) {
}
