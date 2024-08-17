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


import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

   private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Transactional
    public ClienteEntity criar(@RequestBody ClienteEntity clienteEntity){

        return this.clienteService.criar(clienteEntity);
    }

    @GetMapping
    public List<ClienteEntity> obterTodos(){

        return this.clienteService.obterTodos();
    }

    @GetMapping("/pagina-clientes")
    public ResponseEntity<Page<ClienteEntity>> obterClientesPaginados(@PageableDefault(size = 10) Pageable pageable){
        Page<ClienteEntity> clientes = this.clienteService.listaClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{codigo}")
    public ClienteEntity obterPorCodigo(@PathVariable Long codigo){
        return this.clienteService.obterPorCodigo(codigo);
    }

    @PutMapping("/atualiza-cliente")
    public ResponseEntity atualizarCliente(@Valid @RequestBody DadosAtualizacaoClienteDto dadosAtualizacaoClienteDto) {
        return this.clienteService.atualizarCliente(dadosAtualizacaoClienteDto);
    }

}
