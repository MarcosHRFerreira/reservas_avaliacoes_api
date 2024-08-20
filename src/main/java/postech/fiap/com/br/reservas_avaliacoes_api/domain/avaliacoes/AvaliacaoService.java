package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AvaliacaoService {

    public ResponseEntity<?> criar(AvaliacaoEntity avaliacaoEntity);

    public List<AvaliacaoEntity> obterTodos();

    public Page<AvaliacaoEntity> paginaAvaliacoes(@PageableDefault(size = 10) Pageable pageable);

    public AvaliacaoEntity obterPorCodigo(Long codigo);

    public ResponseEntity atualizarAvaliacao(DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto);

}
