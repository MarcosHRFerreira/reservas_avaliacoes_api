package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.DadosAtualizacaoReservaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaService;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @PostMapping(value = "/cadastrar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity cadastrar(@RequestBody ReservaEntity reservaEntity){
        try {
            return this.reservaService.cadastrar(reservaEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/atualizar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto) {
        try {
            return this.reservaService.atualizar(dadosAtualizacaoReservaDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<ReservaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<ReservaEntity> reservas = this.reservaService.obterPaginados(pageable);
        return ResponseEntity.ok(reservas);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity obterPorCodigo(@PathVariable Long codigo) {
        try {
            return this.reservaService.obterPorCodigo(codigo);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome_cliente/{nome}")
    public ResponseEntity obterPorCodigo(@PathVariable String nome) {
        try {
            return this.reservaService.obterPorNomeCliente(nome);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
