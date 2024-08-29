package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.DadosAtualizacaoCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {

   private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody CozinhaEntity cozinhaEntity){

        try {
            return this.cozinhaService.cadastrar(cozinhaEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/atualizar")
    public ResponseEntity<Object>  atualizar(@Valid @RequestBody DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto) {

        try {
            return this.cozinhaService.atualizar(dadosAtualizacaoCozinhaDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<CozinhaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<CozinhaEntity> cozinhas = this.cozinhaService.obterPaginados(pageable);
        return ResponseEntity.ok(cozinhas);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<CozinhaEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            CozinhaEntity cozinha = this.cozinhaService.obterPorCodigo(codigo);
            return ResponseEntity.ok(cozinha);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
