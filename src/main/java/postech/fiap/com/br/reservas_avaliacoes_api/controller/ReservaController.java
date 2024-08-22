package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.CozinhaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.DadosAtualizacaoReservaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody ReservaEntity reservaEntity){
        return this.reservaService.cadastrar(reservaEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto) {
        return this.reservaService.atualizar(dadosAtualizacaoReservaDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<ReservaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<ReservaEntity> reservas = this.reservaService.obterPaginados(pageable);
        return ResponseEntity.ok(reservas);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<ReservaEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            ReservaEntity reserva = this.reservaService.obterPorCodigo(codigo);
            return ResponseEntity.ok(reserva);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
