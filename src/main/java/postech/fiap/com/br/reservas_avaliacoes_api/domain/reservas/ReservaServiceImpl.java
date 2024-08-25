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
import postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas.DadosAtualizacaoMesaDto;
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

            List<MesaEntity> mesasDisponiveis = mesaRepository.findByStatusIsAndId_restaurante(reservaEntity.getId_restaurante(), Status_Mesa.DISPONIVEL);

            // Verificar se há mesas disponíveis
            if (mesasDisponiveis.isEmpty()) {
                return ResponseEntity.badRequest().body("Não há mesas disponíveis para este restaurante.");
            }

            // Verificar se há mesas suficientes
            if (mesasDisponiveis.size() < reservaEntity.getNumero_mesas()) {
                return ResponseEntity.badRequest().body("Não há mesas suficientes disponíveis para este restaurante. Exite(m) "
                        + mesasDisponiveis.size() + " mesa(s) disponível" );
            }

            List<MesaEntity> mesasReservadas = mesasDisponiveis.subList(0, reservaEntity.getNumero_mesas());
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
    public ResponseEntity<?> atualizar(DadosAtualizacaoReservaDto dadosAtualizacaoReservaDto) {
        try {

            if (!clienteRepository.existsById(dadosAtualizacaoReservaDto.id_cliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoReservaDto.id_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!reservaRepository.existsById(dadosAtualizacaoReservaDto.id_reserva())) {
                throw new ValidacaoException("Id da Reserva informado não existe!");
            }

            if (reservaRepository.findByid_clienteAndid_restauranteAnddata_reserva(
                    dadosAtualizacaoReservaDto.id_cliente(),
                    dadosAtualizacaoReservaDto.id_restaurante(),
                    dadosAtualizacaoReservaDto.data_hora().toLocalDate())) {

                var reserva = reservaRepository.getReferenceById(dadosAtualizacaoReservaDto.id_reserva());
                reserva.atualizarInformacoes(dadosAtualizacaoReservaDto);

                //    if (dadosAtualizacaoReservaDto.status().equals(Status_Reserva.CANCELADO)) {
                liberarMesas(dadosAtualizacaoReservaDto.id_restaurante(), dadosAtualizacaoReservaDto.status()
                        , dadosAtualizacaoReservaDto.numero_mesas());
                // }
                return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reserva));
            } else {
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
    public Page<ReservaEntity> obterPaginados(Pageable pageable) {
        //   Sort sort = Sort.by("data_hora").descending();
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
    private void liberarMesas(Long idRestaurante, Status_Reserva status, Integer numeroMesas) {

        List<MesaEntity> mesasReservadas = mesaRepository.findByStatusIsAndId_restaurante(idRestaurante, Status_Mesa.RESERVADA);

        Status_Mesa statusMesa = status.equals(Status_Reserva.CANCELADO) ? Status_Mesa.DISPONIVEL :
                status.equals(Status_Reserva.FINALIZADO) ? Status_Mesa.OCUPADA : null;

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
            mesaRepository.saveAll(mesasReservadas);
        }

    }

}
