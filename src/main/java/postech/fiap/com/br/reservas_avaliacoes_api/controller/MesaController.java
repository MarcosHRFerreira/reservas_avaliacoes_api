package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.DadosAtualizacaoMesaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaService;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ErroExclusaoException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody MesaEntity mesaEntity){
        return this.mesaService.cadastrar(mesaEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizar(@Valid @RequestBody DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto) {
        return this.mesaService.atualizar(dadosAtualizacaoMesaDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<MesaEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<MesaEntity> mesas = this.mesaService.obterPaginados(pageable);
        return ResponseEntity.ok(mesas);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<MesaEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            MesaEntity mesa = this.mesaService.obterPorCodigo(codigo);
            return ResponseEntity.ok(mesa);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
     @DeleteMapping("/excluir/{codigo}")
    public ResponseEntity<Void> excluirMesa(@PathVariable Long codigo){
        try {
            this.mesaService.excluirMesa(codigo);
            return ResponseEntity.noContent().build();
        } catch (ErroExclusaoException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request (erro)
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error (erro inesperado)
        }
    }


}
