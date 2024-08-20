package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import java.time.LocalDateTime;

public record DadosAtualizacaoAvaliacaoDto(
        Long ID_avaliacao,
        Long ID_cliente,
        Long ID_restaurante,
        Long avaliacao,
        String comentario,
        LocalDateTime data_avaliacao
) {
}
