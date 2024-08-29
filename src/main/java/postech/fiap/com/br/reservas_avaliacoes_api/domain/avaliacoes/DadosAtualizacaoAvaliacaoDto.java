package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import java.time.LocalDateTime;

public record DadosAtualizacaoAvaliacaoDto(
        Long idavaliacao,
        Long idcliente,
        Long idrestaurante,
        Long avaliacao,
        String comentario,
        LocalDateTime dataavaliacao
) {
}
