package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

public interface CozinhaService {

    public ResponseEntity<Object> cadastrar(CozinhaEntity cozinhaEntity);

    public Page<CozinhaEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public CozinhaEntity obterPorCodigo(Long codigo);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto);

}
