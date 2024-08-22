package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.DadosAtualizacaoRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.Restaurante_CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

   private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody RestauranteEntity restauranteEntity){
        return this.restauranteService.cadastrar(restauranteEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@Valid @RequestBody DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {
        return this.restauranteService.atualizar(dadosAtualizacaoRestauranteDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<RestauranteEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<RestauranteEntity> restaurantes = this.restauranteService.obterPaginados(pageable);
        return ResponseEntity.ok(restaurantes);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<RestauranteEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            RestauranteEntity restaurante = this.restauranteService.obterPorCodigo(codigo);
            return ResponseEntity.ok(restaurante);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
