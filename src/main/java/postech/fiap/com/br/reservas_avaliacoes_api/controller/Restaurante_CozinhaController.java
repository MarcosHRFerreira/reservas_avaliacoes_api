package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.DadosAtualizacaoRestauranteCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.Restaurante_CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.Restaurante_CozinhaService;

@RestController
@RequestMapping("restaurante_cozinha")
public class Restaurante_CozinhaController {

    private final Restaurante_CozinhaService restaurante_cozinhaService;

    public Restaurante_CozinhaController(Restaurante_CozinhaService restauranteCozinhaService) {
        restaurante_cozinhaService = restauranteCozinhaService;
    }
    @PostMapping
    @Transactional
    public ResponseEntity criar(@RequestBody Restaurante_CozinhaEntity restaurante_cozinhaEntity){
        return this.restaurante_cozinhaService.criar(restaurante_cozinhaEntity);
    }
    @PutMapping("/atualiza-restaurante-cozinha")
    public ResponseEntity<?> atualizarRestaurante_Cozinha(@Valid @RequestBody DadosAtualizacaoRestauranteCozinhaDto dadosAtualizacaoRestauranteCozinhaDto) {
        return this.restaurante_cozinhaService.atualizarRestaurante_Cozinha(dadosAtualizacaoRestauranteCozinhaDto);
    }


}
