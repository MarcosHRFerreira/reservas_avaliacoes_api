package postech.fiap.com.br.reservas_avaliacoes_api.domain.mesas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity,Long> {

    @Query("SELECT COUNT(m) > 0 FROM MesaEntity m WHERE m.id_restaurante = :id_restaurante AND m.numero = :numero")
    boolean findByid_restauranteAndnumero(Long id_restaurante, String numero);
}
