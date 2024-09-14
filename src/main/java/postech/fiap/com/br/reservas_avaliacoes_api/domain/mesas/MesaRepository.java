package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity,Long> {

    @Query("SELECT COUNT(m) > 0 FROM MesaEntity m WHERE m.idrestaurante = :idrestaurante AND m.idmesa = :idmesa")
    boolean findByidrestauranteAndidmesa(Long idrestaurante, Long idmesa);

    @Query("SELECT m FROM MesaEntity m WHERE m.idrestaurante = :idRestaurante AND m.status = :status" )
    List<MesaEntity> findByStatusIsAndId_restaurante(Long idRestaurante, Status_Mesa status  );

}
