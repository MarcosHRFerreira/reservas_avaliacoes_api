package postech.fiap.com.br.reservas_avaliacoes_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.DadosAtualizacaoClienteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@RestController
@RequestMapping("clientes")
public class ClienteController {

   private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @PostMapping(value = "/cadastrar") //,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> cadastrar(@Valid @RequestBody ClienteEntity clienteEntity){
        try {
            return this.clienteService.cadastrar(clienteEntity);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping(value = "/atualizar") //,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> atualizar(@Valid @RequestBody DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto) {

        try {
            return this.clienteService.atualizar(dadosAtualizacaoClienteDto);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<ClienteEntity>> obterPaginados(@PageableDefault Pageable pageable){
        Page<ClienteEntity> clientes = this.clienteService.obterPaginados(pageable);
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<Object> obterPorCodigo(@PathVariable Long codigo) {
        try {
            return this.clienteService.obterPorCodigo(codigo);

        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
