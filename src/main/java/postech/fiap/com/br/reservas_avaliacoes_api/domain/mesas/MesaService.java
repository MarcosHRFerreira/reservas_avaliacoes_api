package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface MesaService {

    public ResponseEntity<Object> cadastrar(MesaEntity mesaEntity);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto);

    public Page<MesaEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public MesaEntity obterPorCodigo(Long codigo);

    public ResponseEntity<Void> excluirMesa(Long codigo);

}
