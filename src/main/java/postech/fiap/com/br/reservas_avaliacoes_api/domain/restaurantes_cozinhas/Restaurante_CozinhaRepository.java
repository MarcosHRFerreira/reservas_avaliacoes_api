package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes_cozinhas;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Restaurante_CozinhaRepository extends JpaRepository<Restaurante_CozinhaEntity, Long> {

   @Query("SELECT COUNT(rc) > 0 FROM Restaurantes_CozinhasEntity rc WHERE rc.id_restaurante = :id_restaurante AND rc.id_cozinha = :id_cozinha")
  boolean findByid_restauranteAndid_cozinha(Long id_restaurante, Long id_cozinha);




}
