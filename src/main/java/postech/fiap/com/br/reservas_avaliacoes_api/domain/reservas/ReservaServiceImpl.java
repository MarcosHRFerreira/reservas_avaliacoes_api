package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.clientes.ClienteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.MesaRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.Status_Mesa;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository, ClienteRepository clienteRepository, MesaRepository mesaRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
        this.mesaRepository = mesaRepository;
    }

    @Override
    @Transactional
    public ResponseEntity cadastrar(ReservaEntity reservaEntity) {
        try {

            if (reservaEntity.getIdcliente() == null || reservaEntity.getIdrestaurante() == null ||
                    reservaEntity.getDatahora() == null || reservaEntity.getNumeropessoas() == null ||
                    reservaEntity.getNumeromesas() == null || reservaEntity.getStatus() == null) {

                throw new ValidacaoException("Os campos esperados são: idcliente, idrestaurante, datahora, numeropessoas, numeromesas, status");
            }

            if (!clienteRepository.existsById(reservaEntity.getIdcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(reservaEntity.getIdrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(
                    reservaEntity.getIdcliente(),
                    reservaEntity.getIdrestaurante(),
                    reservaEntity.getDatahora().toLocalDate())) {
                return ResponseEntity.badRequest().body("Já existe uma reserva com este ID do cliente e ID do restaurante e Data da reserva.");
            }

            List<MesaEntity> mesasDisponiveis = mesaRepository.findByStatusIsAndId_restaurante(reservaEntity.getIdrestaurante(), Status_Mesa.DISPONIVEL);

            // Verificar se há mesas disponíveis
            if (mesasDisponiveis.isEmpty()) {
                return ResponseEntity.badRequest().body("Não há mesas disponíveis para este restaurante.");
            }
            // Verificar se há mesas suficientes
            if (mesasDisponiveis.size() < reservaEntity.getNumeromesas()) {
                return ResponseEntity.badRequest().body("Não há mesas suficientes disponíveis para este restaurante. Exite(m) "
                        + mesasDisponiveis.size() + " mesa(s) disponível" );
            }
            List<MesaEntity> mesasReservadas = mesasDisponiveis.subList(0, reservaEntity.getNumeromesas());
            mesasReservadas.forEach(mesa -> {
                mesa.setStatus(Status_Mesa.RESERVADA);
                mesaRepository.save(mesa);
            });

            reservaRepository.save(reservaEntity);
            return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reservaEntity));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity atualizar(DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto) {
        try {

            if (!clienteRepository.existsById(dadosAtualizacaoReservaDto.idcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoReservaDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!reservaRepository.existsById(dadosAtualizacaoReservaDto.idreserva())) {
                throw new ValidacaoException("Id da Reserva informado não existe!");
            }

            if (reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(
                    dadosAtualizacaoReservaDto.idcliente(),
                    dadosAtualizacaoReservaDto.idrestaurante(),
                    dadosAtualizacaoReservaDto.datahora().toLocalDate())) {

                var reserva = reservaRepository.getReferenceById(dadosAtualizacaoReservaDto.idreserva());
                reserva.atualizarInformacoes(dadosAtualizacaoReservaDto);

                if (dadosAtualizacaoReservaDto.status() == Status_Reserva.CANCELADO) {
                    liberarMesas(dadosAtualizacaoReservaDto.idrestaurante(), dadosAtualizacaoReservaDto.status(),
                            dadosAtualizacaoReservaDto.numeromesas());
                }
                return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reserva));
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ValidacaoException("Erro ao inserir registro: Violação de unicidade.");
        }
    }

    @Override
    public Page<ReservaEntity> obterPaginados(Pageable pageable) {
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize());
        return this.reservaRepository.findAll(paginacao);
    }

    @Override
    public ReservaEntity obterPorCodigo(Long codigo) {
        return reservaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id da Reserva informado não existe!"));
    }

    @Transactional
    public void liberarMesas(Long idRestaurante, Status_Reserva status, Integer numeroMesas) {

        List<MesaEntity> mesasReservadas = mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA);

        Status_Mesa statusMesa = switch (status) {
            case CANCELADO -> Status_Mesa.DISPONIVEL;
            case FINALIZADO -> Status_Mesa.OCUPADA;
            default -> null;
        };
        if (!mesasReservadas.isEmpty()) {
            int mesasLiberadas = 0; // Contador de mesas liberadas

            for (MesaEntity mesa : mesasReservadas) {
                if (mesasLiberadas < numeroMesas) { // Verifica se já liberou a quantidade de mesas
                    mesa.setStatus(statusMesa);
                    mesasLiberadas++; // Incrementa o contador
                } else {
                    // Se já liberou a quantidade de mesas, pode parar o loop
                    break;
                }
            }
        }
       try {
            mesaRepository.saveAll(mesasReservadas);
        }catch  (Exception e) {
            throw new ValidacaoException("Erro ao liberar mesas");
        }
    }

}
