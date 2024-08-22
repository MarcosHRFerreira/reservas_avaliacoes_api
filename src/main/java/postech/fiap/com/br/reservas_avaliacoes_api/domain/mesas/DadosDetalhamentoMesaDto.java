package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

public record DadosDetalhamentoMesaDto(
    Long id_mesa,
    Long id_restaurante,
    String numero,
    Status_Mesa status){

    public DadosDetalhamentoMesaDto(MesaEntity mesa){
        this(mesa.getId_mesa(),
             mesa.getId_restaurante(),
                mesa.getNumero(),
                mesa.getStatus());
    }
}
