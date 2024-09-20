package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import jakarta.validation.ValidationException;
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
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteEntity;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.time.LocalDateTime;
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
                throw new ValidacaoException("Já existe uma reserva com este ID do cliente e ID do restaurante e Data da reserva.");
            }

            List<MesaEntity> mesasDisponiveis = mesaRepository.findByStatusIsAndId_restaurante(reservaEntity.getIdrestaurante(), Status_Mesa.DISPONIVEL);

            ResponseEntity<Object> validacao = validarReserva(reservaEntity);
            if (validacao != null) {
                return validacao; // Retorna a resposta de erro da validação
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

            if (dadosAtualizacaoReservaDto.idcliente() == null || dadosAtualizacaoReservaDto.idrestaurante() == null ||
                    dadosAtualizacaoReservaDto.datahora() == null || dadosAtualizacaoReservaDto.numeropessoas() == null ||
                    dadosAtualizacaoReservaDto.numeromesas() == null || dadosAtualizacaoReservaDto.status() == null ||
                    dadosAtualizacaoReservaDto.idreserva()==null) {

                throw new ValidacaoException("Os campos esperados são: idcliente, idrestaurante, datahora, numeropessoas, numeromesas, status");
            }


            if (!clienteRepository.existsById(dadosAtualizacaoReservaDto.idcliente())) {
                throw new ValidacaoException("Id do Cliente informado não existe!");
            }
            if (!restauranteRepository.existsById(dadosAtualizacaoReservaDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (!reservaRepository.existsById(dadosAtualizacaoReservaDto.idreserva())) {
                throw new ValidacaoException("Id da Reserva informado não existe!");
            }

            // Atualizar os dados da reserva com os dados do DTO

            ReservaEntity reservaEntity = new ReservaEntity();

            reservaEntity.setDatahora(dadosAtualizacaoReservaDto.datahora());
            reservaEntity.setIdrestaurante(dadosAtualizacaoReservaDto.idrestaurante());
            reservaEntity.setNumeromesas(dadosAtualizacaoReservaDto.numeromesas());
            reservaEntity.setNumeropessoas(dadosAtualizacaoReservaDto.numeropessoas());

            ResponseEntity<Object> validacao = validarReserva(reservaEntity);
            if (validacao != null) {
                return validacao; // Retorna a resposta de erro da validação
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
                }else {
                    List<MesaEntity> mesasDisponiveis = mesaRepository.findByStatusIsAndId_restaurante(reservaEntity.getIdrestaurante(), Status_Mesa.DISPONIVEL);
                    List<MesaEntity> mesasReservadas = mesasDisponiveis.subList(0, reservaEntity.getNumeromesas());
                    mesasReservadas.forEach(mesa -> {
                        mesa.setStatus(Status_Mesa.RESERVADA);
                        mesaRepository.save(mesa);
                    });
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

        try {
            Pageable paginacao =
                    PageRequest.of(pageable.getPageNumber(),
                            pageable.getPageSize());
            return this.reservaRepository.findAll(paginacao);
        }catch (IllegalArgumentException e){
            throw new ValidationException("Erro ao obter reservas paginadas", e);
        }
    }

    @Override
    public ResponseEntity<Object> obterPorCodigo(Long codigo) {

        try {
            if(!reservaRepository.existsById(codigo)){
                throw new ValidacaoException("Id da Reserva informado não existe!");
            }
            var reserva= reservaRepository.getReferenceById(codigo);
            return ResponseEntity.ok(new DadosDetalhamentoReservaDto(reserva));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> obterPorNomeCliente(String nome) {

        try {

            List<ReservaEntity> reserva = reservaRepository.findReservasByNomeCliente(nome);

            if (reserva.isEmpty()) {
                throw new ValidacaoException("Nenhum nome encontrado na reserva.");
            }

            return ResponseEntity.ok(reserva);

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }


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

    public ResponseEntity<Object> validarReserva(ReservaEntity reservaEntity) {
        // Verificar se a data da reserva é válida
        LocalDateTime now = LocalDateTime.now();
        if (reservaEntity.getDatahora().isBefore(now)) {
            return ResponseEntity.badRequest().body("Não será permitido datas passadas na reserva.");
        }

        // Verificar se há mesas disponíveis
        List<MesaEntity> mesasDisponiveis = mesaRepository.findByStatusIsAndId_restaurante(reservaEntity.getIdrestaurante(), Status_Mesa.DISPONIVEL);
        if (mesasDisponiveis.isEmpty()) {
            return ResponseEntity.badRequest().body("Não há mesas disponíveis para este restaurante.");
        }

        // Verificar se o número de mesas é válido
        if (mesasDisponiveis.size() < reservaEntity.getNumeromesas()) {
            return ResponseEntity.badRequest().body("Não há mesas suficientes disponíveis para este restaurante. Exite(m) "
                    + mesasDisponiveis.size() + " mesa(s) disponível" );
        }

        // Verificar se o número de pessoas é válido
        if (reservaEntity.getNumeropessoas() > reservaEntity.getNumeromesas() * 4) {
            return ResponseEntity.badRequest().body("A quantidade de pessoas na reserva excede a capacidade das mesas disponíveis. " +
                    "Cada mesa suporta no máximo 4 pessoas. Mesa(s) disponível - "
                    + mesasDisponiveis.size() );
        }

        // Se todas as validações passarem, retorna null para indicar sucesso
        return null;
    }

}
