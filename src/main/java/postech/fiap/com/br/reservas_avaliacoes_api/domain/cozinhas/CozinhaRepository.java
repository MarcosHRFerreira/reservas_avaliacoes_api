package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<CozinhaEntity, Long> {

}
