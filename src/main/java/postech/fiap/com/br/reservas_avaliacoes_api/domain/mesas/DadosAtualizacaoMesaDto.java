package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

public record DadosAtualizacaoMesaDto(
        Long id_mesa,
        Long id_restaurante,
        String numero,
        Status_Mesa status
) {
}
