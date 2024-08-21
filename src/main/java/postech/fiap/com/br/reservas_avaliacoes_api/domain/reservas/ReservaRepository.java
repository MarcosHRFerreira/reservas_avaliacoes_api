package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    @Query("SELECT COUNT(rs) > 0 FROM ReservaEntity rs WHERE rs.id_cliente= :id_cliente AND rs.id_restaurante = :id_restaurante AND date(rs.data_hora) = :data_hora")
    boolean findByid_clienteAndid_restauranteAnddata_reserva(Long id_cliente, Long id_restaurante, LocalDate data_hora);

}
