package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantescozinhas;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteCozinhaRepository extends JpaRepository<RestauranteCozinhaEntity, Long> {

   @Query("SELECT COUNT(rc) > 0 FROM RestaurantesCozinhasEntity rc WHERE rc.idrestaurante = :idrestaurante AND rc.idcozinha = :idcozinha")
  boolean findByidrestauranteAndidcozinha(Long idrestaurante, Long idcozinha);




}
