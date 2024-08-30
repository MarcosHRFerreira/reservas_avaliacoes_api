package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

public record EstatisticaRestauranteDto(

        String nome,
        Long totalAvaliacoes,
        Double mediaAvaliacao

) {
}
