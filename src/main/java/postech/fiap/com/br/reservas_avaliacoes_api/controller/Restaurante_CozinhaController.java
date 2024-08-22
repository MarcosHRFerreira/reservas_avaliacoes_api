package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.DadosAtualizacaoRestauranteCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.Restaurante_CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.Restaurante_CozinhaService;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("restaurante_cozinha")
public class Restaurante_CozinhaController {

    private final Restaurante_CozinhaService restaurante_cozinhaService;

    public Restaurante_CozinhaController(Restaurante_CozinhaService restauranteCozinhaService) {
        restaurante_cozinhaService = restauranteCozinhaService;
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastar(@RequestBody Restaurante_CozinhaEntity restaurante_cozinhaEntity){
        return this.restaurante_cozinhaService.cadastrar(restaurante_cozinhaEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@Valid @RequestBody DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto) {
        return this.restaurante_cozinhaService.atualizar(dadosAtualizacaoRestauranteCozinhaDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<Restaurante_CozinhaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<Restaurante_CozinhaEntity> restaurante_cozinha = this.restaurante_cozinhaService.obterPaginados(pageable);
        return ResponseEntity.ok(restaurante_cozinha);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<Restaurante_CozinhaEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            Restaurante_CozinhaEntity restaurante_cozinha = this.restaurante_cozinhaService.obterPorCodigo(codigo);
            return ResponseEntity.ok(restaurante_cozinha);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
