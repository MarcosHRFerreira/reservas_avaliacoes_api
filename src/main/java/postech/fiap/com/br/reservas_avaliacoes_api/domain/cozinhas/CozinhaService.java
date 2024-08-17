package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CozinhaService {

    public CozinhaEntity criar(CozinhaEntity cozinhaEntity);

    public List<CozinhaEntity> obterTodos();

    public Page<CozinhaEntity> listaCozinhas(@PageableDefault(size = 10) Pageable pageable);

    public CozinhaEntity obterPorCodigo(Long codigo);

    public ResponseEntity<?> atualizarCozinha(DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto);

}
