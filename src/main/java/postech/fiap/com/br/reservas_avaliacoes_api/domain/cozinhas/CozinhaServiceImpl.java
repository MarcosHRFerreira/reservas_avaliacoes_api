package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.fiap.com.br.reservas_avaliacoes_api.exception.ValidacaoException;

@Service
public class CozinhaServiceImpl implements CozinhaService {

    private final CozinhaRepository cozinhaRepository ;

    public CozinhaServiceImpl(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Object> cadastrar(CozinhaEntity cozinhaEntity) {
        try {
            if (cozinhaRepository.existsByEspecialidade(cozinhaEntity.getEspecialidade())) {
                throw new ValidacaoException("Especialidade já existe na base cozinha");
            }
            cozinhaRepository.save(cozinhaEntity);
            return ResponseEntity.ok(new DadosDetalhamentoCozinhaDto(cozinhaEntity));

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar : " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public ResponseEntity<Object> atualizar(DadosAtualizacaoCozinhaDto dadosAtualizacaoCozinhaDto) {
        try {
            if (!cozinhaRepository.existsById(dadosAtualizacaoCozinhaDto.idcozinha())) {
                throw new ValidacaoException("Id da Cozinha informado não existe!");
            }
            var cozinha=cozinhaRepository.getReferenceById (dadosAtualizacaoCozinhaDto.idcozinha());
            cozinha.atualizarInformacoes(dadosAtualizacaoCozinhaDto);
            return ResponseEntity.ok(new DadosDetalhamentoCozinhaDto (cozinha));
        }catch (ValidacaoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    public Page<CozinhaEntity> obterPaginados(Pageable pageable) {
        Sort sort = Sort.by("especialidade").ascending();
        Pageable paginacao =
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), sort);
        return this.cozinhaRepository.findAll(paginacao);
    }
    @Override
    public CozinhaEntity obterPorCodigo(Long codigo) {
        return cozinhaRepository.findById(codigo)
                .orElseThrow(() -> new ValidacaoException("Id da Cozinha informado não existe!"));
    }
}
