package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.DadosAtualizacaoRestauranteCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.RestauranteCozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas.RestauranteCozinhaService;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("restaurante_cozinha")
public class RestauranteCozinhaController {

    private final RestauranteCozinhaService restaurantecozinhaService;

    public RestauranteCozinhaController(RestauranteCozinhaService restauranteCozinhaService) {
        restaurantecozinhaService = restauranteCozinhaService;
    }

    @PostMapping(value = "/cadastrar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastar(@Valid @RequestBody RestauranteCozinhaEntity restaurantecozinhaEntity ){
        try {
            return this.restaurantecozinhaService.cadastrar(restaurantecozinhaEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/atualizar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> atualizar(@Valid @RequestBody DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto) {
        try {
            return this.restaurantecozinhaService.atualizar(dadosAtualizacaoRestauranteCozinhaDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<RestauranteCozinhaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<RestauranteCozinhaEntity> restaurantecozinha = this.restaurantecozinhaService.obterPaginados(pageable);
        return ResponseEntity.ok(restaurantecozinha);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<RestauranteCozinhaEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            RestauranteCozinhaEntity restaurantecozinha = this.restaurantecozinhaService.obterPorCodigo(codigo);
            return ResponseEntity.ok(restaurantecozinha);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
