package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;

import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;

public record DadosDetalhamentoCozinhaDto(

        Long id_cozinha,
        String especialidade) {

    public DadosDetalhamentoCozinhaDto(CozinhaEntity cozinha){
           this(cozinha.getId_cozinha(),
           cozinha.getEspecialidade());
    }

}
