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

import java.util.List;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {

   private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @PostMapping
    @Transactional
    public CozinhaEntity criar(@RequestBody CozinhaEntity cozinhaEntity){
        return this.cozinhaService.criar(cozinhaEntity);
    }
    @GetMapping
    public List<CozinhaEntity> obterTodos(){
        return this.cozinhaService.obterTodos();
    }
    @GetMapping("/pagina-cozinha")
    public ResponseEntity<Page<CozinhaEntity>> obterCozinhasPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<CozinhaEntity> cozinhas = this.cozinhaService.listaCozinhas(pageable);
        return ResponseEntity.ok(cozinhas);
    }
    @GetMapping("/{codigo}")
    public CozinhaEntity obterPorCodigo(@PathVariable Long codigo){
        return this.cozinhaService.obterPorCodigo(codigo);
    }
    @PutMapping("/atualiza-cozinha")
    public ResponseEntity atualizarCozinha(@Valid @RequestBody DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto) {
        return this.cozinhaService.atualizarCozinha(dadosAtualizacaoCozinhaDto);
    }
}
