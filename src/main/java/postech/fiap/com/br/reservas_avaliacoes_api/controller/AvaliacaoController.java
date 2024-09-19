package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.AvaliacaoEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.AvaliacaoService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.DadosAtualizacaoAvaliacaoDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.EstatisticaRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ErroExclusaoException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody AvaliacaoEntity avaliacaoEntity) {
        try {
            return this.avaliacaoService.cadastrar(avaliacaoEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> atualizar(@Valid @RequestBody DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto) {
        try {
            return this.avaliacaoService.atualizar(dadosAtualizacaoAvalizacaoDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/paginar")
    public ResponseEntity<Page<AvaliacaoEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable) {

        Page<AvaliacaoEntity> avaliacao = this.avaliacaoService.obterPaginados(pageable);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity obterPorCodigo(@PathVariable Long codigo) {
        try {
            return this.avaliacaoService.obterPorCodigo(codigo);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{codigo}")
    public ResponseEntity<Object> excluirAvaliacao(@PathVariable Long codigo) {
        try {
            return this.avaliacaoService.excluirAvaliacao(codigo);
        } catch (ErroExclusaoException e) {
            return ResponseEntity.badRequest().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error (erro inesperado)
        }
    }

    @GetMapping("/estatisticas/{idRestaurante}")
    public ResponseEntity<List<EstatisticaRestauranteDto>> getEstatisticasRestauranteUltimos30Dias(@PathVariable Long idRestaurante) {
        try {
            return avaliacaoService.getEstatisticasRestauranteUltimos30Dias(idRestaurante);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estatisticas/todos")
    public ResponseEntity<List<EstatisticaRestauranteDto>> getEstatisticasRestauranteUltimos30DiasTodos() {
        try {
            return avaliacaoService.getEstatisticasRestauranteUltimos30DiasTodos();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
