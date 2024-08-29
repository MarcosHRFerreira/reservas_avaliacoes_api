package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

public record DadosDetalhamentoMesaDto(
    Long idmesa,
    Long idrestaurante,
    String numero,
    Status_Mesa status){

    public DadosDetalhamentoMesaDto(MesaEntity mesa){
        this(mesa.getIdmesa(),
             mesa.getIdrestaurante(),
                mesa.getNumero(),
                mesa.getStatus());
    }
}
