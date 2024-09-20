package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }
    @Override
    @Transactional
    public ResponseEntity<Object> cadastrar(RestauranteEntity restauranteEntity) {
        try {
            if (restauranteRepository.existsBynomeAndEmail(restauranteEntity.getNome(),restauranteEntity.getEmail())) {
                throw new ValidacaoException("Já existe o Nome do Restaurante com o email na base");
            }
            if (restauranteRepository.existsByEmail (restauranteEntity.getEmail()))
            {
                throw new ValidacaoException("Já existe o email " + restauranteEntity.getEmail() + " na base de restaurantes");
            }
            var restaturante=restauranteRepository.save(restauranteEntity);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaturante));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar restaurante: " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoRestauranteDto dadosAtualizacaoRestauranteDto) {
        try {
            if (!restauranteRepository.existsById(dadosAtualizacaoRestauranteDto.idrestaurante())) {
                throw new ValidacaoException("Id do Restaurante informado não existe!");
            }
            var restaurante=restauranteRepository.getReferenceById (dadosAtualizacaoRestauranteDto.idrestaurante());
            restaurante.atualizarInformacoes(dadosAtualizacaoRestauranteDto);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaurante));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> obterPorNome(String nome) {

            try {
                // Busca os restaurantes com o nome que contém a string informada
                List<RestauranteEntity> restaurantes = restauranteRepository.findByNomeContaining(nome);

                if (restaurantes.isEmpty()) {
                    throw new ValidacaoException("Nenhum restaurante encontrado com o nome informado.");
                }

                // Retorna a lista de restaurantes encontrados
                return ResponseEntity.ok(restaurantes);

            } catch (ValidacaoException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

    }

    @Override
    public ResponseEntity<Object> obterPorUF(String uf) {

        try {
            // Busca os restaurantes com o nome que contém a string informada
            List<RestauranteEntity> restaurantes = restauranteRepository.findRestauranteUf(uf);

            if (restaurantes.isEmpty()) {
                throw new ValidacaoException("Nenhum restaurante encontrado com a UF informada.");
            }

            // Retorna a lista de restaurantes encontrados
            return ResponseEntity.ok(restaurantes);

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<Object> obterRestaurantesPorCozinha(String cozinha) {
        try {
            // Busca os restaurantes com a cozinha que contém a string informada
            List<RestauranteEntity> restaurantes = restauranteRepository.findRestaurantesCozinha(cozinha);


            if (restaurantes.isEmpty()) {
                throw new ValidacaoException("Nenhum restaurante encontrado com a cozinha informada.");
            }

            // Retorna a lista de restaurantes encontrados
            return ResponseEntity.ok(restaurantes);

        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
       public Page<RestauranteEntity> obterPaginados(Pageable pageable) {

        try {
            Sort sort = Sort.by("nome").ascending();
            Pageable paginacao =
                    PageRequest.of(pageable.getPageNumber(),
                            pageable.getPageSize(), sort);
            return this.restauranteRepository.findAll(paginacao);
        }catch (IllegalArgumentException e){
            throw new ValidationException("Erro ao obter restaurantes paginados", e);
        }
    }

    @Override
    public ResponseEntity obterPorCodigo(Long codigo) {

        try{
            if (!restauranteRepository.existsById(codigo)) {
                throw new ValidacaoException("Id do restaurante informado não existe!");
            }
            var restaurante = restauranteRepository.getReferenceById(codigo);
            return ResponseEntity.ok(new DadosDetalhamentoRestauranteDto(restaurante));

        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }



}
