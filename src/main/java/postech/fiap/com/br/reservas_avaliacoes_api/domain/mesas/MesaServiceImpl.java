package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes.RestauranteRepository;
import postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas.DadosDetalhamentoRestauranteCozinha;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;
    private final RestauranteRepository restauranteRepository;

    public MesaServiceImpl(MesaRepository mesaRepository, RestauranteRepository restauranteRepository) {
        this.mesaRepository = mesaRepository;
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    public ResponseEntity cadastrar(MesaEntity mesaEntity) {
        try {
            if (!restauranteRepository.existsById(mesaEntity.getId_restaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            if (mesaRepository.findByid_restauranteAndnumero(
                    mesaEntity.getId_restaurante(),
                    mesaEntity.getNumero())) {
                return ResponseEntity.badRequest().body("Já existe um registro com este ID de restaurante número de mesa.");
            }else {
                mesaRepository.save(mesaEntity);
                return ResponseEntity.ok(new DadosDetalhamentoMesaDto(mesaEntity));
            }

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> atualizar(DadosAtualizacaoMesaDto dadosAtualizacaoMesaDto) {
        try {
            if (!restauranteRepository.existsById(dadosAtualizacaoMesaDto.id_mesa())) {
                throw new ValidacaoException("Id da Mesa informado não existe!");
            }
            if (!mesaRepository.findByid_restauranteAndnumero(
                    dadosAtualizacaoMesaDto.id_restaurante(),
                    dadosAtualizacaoMesaDto.numero())) {

                return ResponseEntity.badRequest().body("Não existe um registro com ID de restaurante e número de mesa.");
            }
            var mesa = mesaRepository.getReferenceById(dadosAtualizacaoMesaDto.id_mesa());
            mesa.atualizarInformacoes(dadosAtualizacaoMesaDto);
            return ResponseEntity.ok(new DadosDetalhamentoMesaDto(mesa));

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Override
    public Page<MesaEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("id_mesa").ascending();
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
}
