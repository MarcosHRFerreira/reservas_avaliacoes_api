package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservaService {

    public ResponseEntity cadastrar(ReservaEntity reservaEntity);

    public ResponseEntity atualizar(DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto);

    public Page<ReservaEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public ReservaEntity obterPorCodigo(Long codigo);

}
