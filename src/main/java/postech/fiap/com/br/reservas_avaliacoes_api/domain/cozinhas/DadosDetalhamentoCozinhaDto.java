package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;


public record DadosDetalhamentoCozinhaDto(
        Long idcozinha,
        String especialidade) {

    public DadosDetalhamentoCozinhaDto(CozinhaEntity cozinha){
           this(cozinha.getIdcozinha(),
           cozinha.getEspecialidade());
    }

}
