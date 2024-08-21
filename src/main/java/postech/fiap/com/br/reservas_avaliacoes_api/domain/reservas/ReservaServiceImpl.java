package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes.DadosDetalhamentoAvalizacaoDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas.DadosAtualizacaoCozinhaDto;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;
@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ResponseEntity criar(ReservaEntity reservaEntity) {

        try {
            if (!clienteRepository.existsById(reservaEntity.getId_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(reservaEntity.getId_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(
                    reservaEntity.getId_cliente(),
                    reservaEntity.getId_restaurante(),
                    reservaEntity.getData_hora().toLocalDate())) {
                return ResponseEntity.badRequest().body("Já existe uma reserva com este ID do cliente e ID do restaurante e Data da reserva.");
            }
            reservaRepository.save(reservaEntity);
            return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reservaEntity));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // log.error("Erro ao inserir registro: {}", e.getMessage());
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> atualizarReserva(DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto) {
        try {
            if (!clienteRepository.existsById(dadosAtualizacaoReservaDto.id_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoReservaDto.id_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoReservaDto.id_reserva())) {
                throw new ValidacaoException("Id da Reserva informado não existe!");
            }

            if (reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(
                    dadosAtualizacaoReservaDto.id_cliente(),
                    dadosAtualizacaoReservaDto.id_restaurante(),
                    dadosAtualizacaoReservaDto.data_hora().toLocalDate())) {
                var reserva = reservaRepository.getReferenceById(dadosAtualizacaoReservaDto.id_reserva());
                reserva.atualizarInformacoes(dadosAtualizacaoReservaDto);
                return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reserva));
            }else {
                return ResponseEntity.notFound().build();
            }


        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {
            // log.error("Erro ao inserir registro: {}", e.getMessage());
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");

        }
    }

    @Override
    public List<ReservaEntity> obterTodos() {
        return this.reservaRepository.findAll();
    }

    @Override
    public Page<ReservaEntity> obterReservasPaginados(Pageable pageable) {
     //   Sort sort = Sort.by("data_hora").descending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize());
        return this.reservaRepository.findAll(paginacao);
    }

    @Override
    public ReservaEntity obterPorCodigo(Long codigo) {
        if (!restauranteRepository.existsById(codigo)) {
            throw new ValidacaoException("Id da Reserva informado não existe!");
        }
        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id da Reserva informado não existe!"));
    }

}
