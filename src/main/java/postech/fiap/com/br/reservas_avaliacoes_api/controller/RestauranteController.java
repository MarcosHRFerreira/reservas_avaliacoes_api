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
    @GetMapping(value = "/paginar")
    public ResponseEntity<Page<RestauranteEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<RestauranteEntity> restaurantes = this.restauranteService.obterPaginados(pageable);
        return ResponseEntity.ok(restaurantes);
    }
    @GetMapping(value = "/{codigo}")
    public ResponseEntity<Object> obterPorCodigo(@PathVariable Long codigo) {
        try {
            return this.restauranteService.obterPorCodigo(codigo);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<Object> obterPorNome (@PathVariable String nome) {
        try {
            return this.restauranteService.obterPorNome(nome);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/UF/{UF}")
    public ResponseEntity<Object> obterPorUF (@PathVariable String UF) {
        try {
            return this.restauranteService.obterPorUF(UF);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/cozinha/{idcozinha}")
    public ResponseEntity<Object> obterRestaurantesPorCozinha(@PathVariable Long idcozinha) {
        try {
            return this.restauranteService.obterRestaurantesPorCozinha(idcozinha);

        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();

        }
    }

}
