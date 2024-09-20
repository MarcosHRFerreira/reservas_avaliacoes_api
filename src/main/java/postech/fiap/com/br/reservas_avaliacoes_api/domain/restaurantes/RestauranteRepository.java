package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository <RestauranteEntity, Long> {

    boolean existsBynomeAndEmail(String nome, String email);

    boolean existsByEmail(String email);

    boolean existsByNome(String nome);

    List<RestauranteEntity> findByNomeContaining(String nome);

    @Query(value = "SELECT * FROM restaurantes WHERE lower(uf) LIKE '%'||lower(:uf)||'%'", nativeQuery = true)
    List<RestauranteEntity> findRestauranteUf(String uf);

    @Query(value = "SELECT * FROM restaurantes WHERE lower(cozinha) LIKE '%'||lower(:cozinha)||'%'", nativeQuery = true)
    List<RestauranteEntity> findRestaurantesCozinha(String cozinha);
}
