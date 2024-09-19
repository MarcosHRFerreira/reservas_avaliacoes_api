package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ErroExclusaoException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@RestController
@RequestMapping("mesas")
public class MesaController {

    private final MesaService mesaService ;
    private final MesaRepository mesaRepository;
    private final RestauranteRepository restauranteRepository;

    public MesaController(MesaService mesaService, MesaRepository mesaRepository, RestauranteRepository restauranteRepository    ) {
        this.mesaService = mesaService;
        this.mesaRepository = mesaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @PostMapping(value = "/cadastrar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody MesaEntity mesaEntity){
        return this.mesaService.cadastrar(mesaEntity);
    }
    @PutMapping(value = "/atualizar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/statusmesa/{idrestaurante},{status}")
    public ResponseEntity<Object> obterPorStatus(@PathVariable Long idrestaurante, @PathVariable Status_Mesa status) {

        try {
            List<MesaEntity> mesas = mesaService.obterPorStatus(idrestaurante, status);
            if (mesas.isEmpty()) {
                return ResponseEntity.badRequest().body("Não há nenhuma mesa com o ID de restaurante " + idrestaurante + " e status " + status + ".");
            } else {
                return ResponseEntity.ok(mesas); // Retorna a lista de mesas
            }
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
