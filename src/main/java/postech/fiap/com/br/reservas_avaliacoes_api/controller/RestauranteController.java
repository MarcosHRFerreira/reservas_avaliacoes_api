package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.DadosAtualizacaoRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteService;

import java.util.List;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

   private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @Transactional
    public RestauranteEntity criar(@RequestBody RestauranteEntity restauranteEntity){

        return this.restauranteService.criar(restauranteEntity);
    }

    @GetMapping
    public List<RestauranteEntity> obterTodos(){

        return this.restauranteService.obterTodos();
    }

    @GetMapping("/pagina-restaurantes")
    public ResponseEntity<Page<RestauranteEntity>> obterRestaurantesPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<RestauranteEntity> restaurantes = this.restauranteService.listaRestaurantes(pageable);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{codigo}")
    public RestauranteEntity obterPorCodigo(@PathVariable Long codigo){
        return this.restauranteService.obterPorCodigo(codigo);
    }

    @PutMapping("/atualiza-restaurante")
    public ResponseEntity<?> atualizarRestaurante(@Valid @RequestBody DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {
        return this.restauranteService.atualizarRestaurante(dadosAtualizacaoRestauranteDto);
    }

}
