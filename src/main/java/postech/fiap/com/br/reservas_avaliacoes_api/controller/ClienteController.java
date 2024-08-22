package postech.fiap.com.br.reservas_avaliacoes_api.controller;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteService;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.DadosAtualizacaoClienteDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas.ReservaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;


import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

   private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody ClienteEntity clienteEntity){
        return this.clienteService.cadastrar(clienteEntity);
    }
    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto) {
        return this.clienteService.atualizar(dadosAtualizacaoClienteDto);
    }
    @GetMapping("/paginar")
    public ResponseEntity<Page<ClienteEntity>> obterPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<ClienteEntity> clientes = this.clienteService.obterPaginados(pageable);
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteEntity> obterPorCodigo(@PathVariable Long codigo) {
        try {
            ClienteEntity cliente = this.clienteService.obterPorCodigo(codigo);
            return ResponseEntity.ok(cliente);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
