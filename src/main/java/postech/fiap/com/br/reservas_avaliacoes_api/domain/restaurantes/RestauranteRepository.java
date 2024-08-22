package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

    boolean existsBynomeAndEmail(String nome, String email);

    boolean existsByEmail(String email);
}
