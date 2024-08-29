package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

public record DadosAtualizacaoMesaDto(
        Long idmesa,
        Long idrestaurante,
        String numero,
        Status_Mesa status
) {
}
