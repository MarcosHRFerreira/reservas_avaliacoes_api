package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface AvaliacaoService {

    public ResponseEntity<Object> cadastrar(AvaliacaoEntity avaliacaoEntity);

    public Page<AvaliacaoEntity> obterPaginados(@PageableDefault(size = 10) Pageable pageable);

    public AvaliacaoEntity obterPorCodigo(Long codigo);

    public ResponseEntity<Object> atualizar(DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto);

    public ResponseEntity<Void> excluirAvaliacao(Long codigo);

}
