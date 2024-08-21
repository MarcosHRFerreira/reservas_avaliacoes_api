package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {

    public ResponseEntity criar(ReservaEntity reservaEntity);

    public ResponseEntity<?> atualizarReserva(DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto);

    public List<ReservaEntity> obterTodos();

    public Page<ReservaEntity> obterReservasPaginados(@PageableDefault(size = 10) Pageable pageable);

    public ReservaEntity obterPorCodigo(Long codigo);



}
