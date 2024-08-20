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

import java.util.List;

@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }
    @PostMapping
    @Transactional
    public ResponseEntity criar(@RequestBody AvaliacaoEntity avaliacaoEntity){
        return this.avaliacaoService.criar(avaliacaoEntity);
    }
    @PutMapping("/atualiza-avaliacao")
    public ResponseEntity atualizarAvaliacao(@Valid @RequestBody DadosAtualizacaoAvaliacaoDto dadosAtualizacaoAvalizacaoDto) {
        return this.avaliacaoService.atualizarAvaliacao(dadosAtualizacaoAvalizacaoDto);
    }
    @GetMapping("/{codigo}")
    public AvaliacaoEntity obterPorCodigo(@PathVariable Long codigo){
        return this.avaliacaoService.obterPorCodigo(codigo);
    }
    @GetMapping("/pagina-cozinha")
    public ResponseEntity<Page<AvaliacaoEntity>> paginaAvaliacoes(@PageableDefault(size = 10) Pageable pageable){
        Page<AvaliacaoEntity> avaliacoes = this.avaliacaoService.paginaAvaliacoes(pageable);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping
    public List<AvaliacaoEntity> obterTodos(){
         return this.avaliacaoService.obterTodos();
    }
}
