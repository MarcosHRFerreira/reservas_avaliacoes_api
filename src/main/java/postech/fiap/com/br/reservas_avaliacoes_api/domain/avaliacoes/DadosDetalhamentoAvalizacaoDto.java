package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import java.time.LocalDateTime;

public record DadosDetalhamentoAvalizacaoDto(
        Long ID_avaliacao,
        Long ID_cliente,
        Long ID_restaurante,
        Long avaliacao,
        String comentario,
        LocalDateTime data_avaliacao
) {
    public DadosDetalhamentoAvalizacaoDto(AvaliacaoEntity dados) {

        this(dados.getID_avaliacao(),
                dados.getID_cliente(),
                dados.getID_restaurante(),
                dados.getAvaliacao(),
                dados.getComentario(),
                dados.getData_avaliacao()
        );
    }
}
