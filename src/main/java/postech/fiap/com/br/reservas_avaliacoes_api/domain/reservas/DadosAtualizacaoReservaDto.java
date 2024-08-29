package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import java.time.LocalDateTime;

public record DadosAtualizacaoReservaDto(
        Long idreserva,
        Long idcliente,
        Long idrestaurante,
        LocalDateTime datahora,
        Integer numeropessoas,
        Integer numeromesas,
        Status_Reserva status)
         {
}
