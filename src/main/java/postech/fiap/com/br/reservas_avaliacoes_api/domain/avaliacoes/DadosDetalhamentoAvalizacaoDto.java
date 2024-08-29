package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import java.time.LocalDateTime;

public record DadosDetalhamentoAvalizacaoDto(
        Long idavaliacao,
        Long idcliente,
        Long idrestaurante,
        Long avaliacao,
        String comentario,
        LocalDateTime dataavaliacao
) {
    public DadosDetalhamentoAvalizacaoDto(AvaliacaoEntity dados) {
        this(dados.getidavaliacao(),
                dados.getidcliente(),
                dados.getidrestaurante(),
                dados.getavaliacao(),
                dados.getcomentario(),
                dados.getdataavaliacao()
        );
    }
}
