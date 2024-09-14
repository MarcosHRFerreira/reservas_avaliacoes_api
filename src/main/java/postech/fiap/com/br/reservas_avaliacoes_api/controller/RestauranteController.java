package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.DadosAtualizacaoRestauranteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteService;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

   private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }
    @PostMapping(value = "/cadastrar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastrar(@Valid @RequestBody RestauranteEntity restauranteEntity){
        try {
            return this.restauranteService.cadastrar(restauranteEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/atualizar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> atualizar(@Valid @RequestBody DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {
        try {
            return this.restauranteService.atualizar(dadosAtualizacaoRestauranteDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
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
