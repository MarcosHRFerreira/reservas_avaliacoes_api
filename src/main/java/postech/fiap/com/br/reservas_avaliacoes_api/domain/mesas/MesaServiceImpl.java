package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ErroExclusaoException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.MesaNaoEncontradaException;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;
    private final RestauranteRepository restauranteRepository;

    public MesaServiceImpl(MesaRepository mesaRepository, RestauranteRepository restauranteRepository) {
        this.mesaRepository = mesaRepository;
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<Object> cadastrar(MesaEntity mesaEntity) {
        try {
            if (!restauranteRepository.existsById(mesaEntity.getIdrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (mesaRepository.findByidrestauranteAndidmesa(
                    mesaEntity.getIdrestaurante(),
                    mesaEntity.getIdmesa())) {
                return ResponseEntity.badRequest().body("Já existe um registro com este idrestaurante e idmesa.");
            }else {
                mesaRepository.save(mesaEntity);
                return ResponseEntity.ok(new DadosDetalhamentoMesaDto(mesaEntity));
            }
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto) {
        try {
            if (!mesaRepository.existsById(dadosAtualizacaoMesaDto.idmesa())) {
                throw new ValidacaoException("Id da Mesa informado não existe!");
            }
            if (!mesaRepository.findByidrestauranteAndidmesa(
                    dadosAtualizacaoMesaDto.idrestaurante(),
                    dadosAtualizacaoMesaDto.idmesa())) {

                return ResponseEntity.badRequest().body("Não existe um registro com ID de restaurante e número de mesa.");
            }
            var mesa = mesaRepository.getReferenceById(dadosAtualizacaoMesaDto.idmesa());

            if (mesa.getStatus() == Status_Mesa.RESERVADA) {
                return ResponseEntity.badRequest().body("Mesa reservada, não pode ser atualizada para disponível. Libere a mesa no modulo de Reservas");
            }
            mesa.atualizarInformacoes(dadosAtualizacaoMesaDto);
            return ResponseEntity.ok(new DadosDetalhamentoMesaDto(mesa));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    public Page<MesaEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("numero").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.mesaRepository.findAll(paginacao);
    }
    @Override
    public MesaEntity obterPorCodigo(Long codigo) {
        return mesaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id do Mesa informado não existe!"));
    }

    @Override
    public ResponseEntity<Void> excluirMesa(Long codigo) {
        try {
            mesaRepository.deleteById(codigo);
            return ResponseEntity.noContent().build();
        }catch (ErroExclusaoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public List<MesaEntity> obterPorStatus(Long idrestaurante, Status_Mesa status) {
        try {
            List<MesaEntity> mesas = mesaRepository.findByStatusIsAndId_restaurante(idrestaurante, status);

            if (mesas.isEmpty()) {
                throw new ValidacaoException("Não há nenhuma mesa com o ID de restaurante " + idrestaurante + " e status " + status + ".");
            } else {
                return mesas;
            }
        } catch (MesaNaoEncontradaException e) {
            throw new ValidacaoException("Não há nenhuma mesa com o ID de restaurante ");

        }
    }
}
