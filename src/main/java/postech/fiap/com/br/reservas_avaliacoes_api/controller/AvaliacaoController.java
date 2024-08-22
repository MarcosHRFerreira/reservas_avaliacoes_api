package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.AvaliacaoEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.AvaliacaoService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.DadosAtualizacaoAvaliacaoDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody AvaliacaoEntity avaliacaoEntity){
        return this.avaliacaoService.cadastrar(avaliacaoEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto) {
        return this.avaliacaoService.atualizar(dadosAtualizacaoAvalizacaoDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<AvaliacaoEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<AvaliacaoEntity> avaliacao = this.avaliacaoService.obterPaginados(pageable);
        return ResponseEntity.ok(avaliacao);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<AvaliacaoEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            AvaliacaoEntity avaliacao = this.avaliacaoService.obterPorCodigo(codigo);
            return ResponseEntity.ok(avaliacao);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
