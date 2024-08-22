package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.AvaliacaoEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.DadosAtualizacaoCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {

   private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody CozinhaEntity cozinhaEntity){
        return this.cozinhaService.cadastrar(cozinhaEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto) {
        return this.cozinhaService.atualizar(dadosAtualizacaoCozinhaDto);
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
