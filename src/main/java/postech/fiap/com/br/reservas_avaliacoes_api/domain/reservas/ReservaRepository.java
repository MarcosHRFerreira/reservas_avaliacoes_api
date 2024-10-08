package postech.fiap.com.br.reservas_avaliacoes_api.domain.reservas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    @Query("SELECT COUNT(rs) > 0 FROM ReservaEntity rs WHERE rs.idcliente= :idcliente AND rs.idrestaurante = :idrestaurante AND date(rs.datahora) = :datahora")
    boolean findByid_clienteAndid_restauranteAnddata_reserva(Long idcliente, Long idrestaurante, LocalDate datahora);

    @Query(value = "SELECT * FROM reservas  WHERE idcliente IN (SELECT idcliente FROM clientes WHERE lower(nome) LIKE '%'||lower(:nome)||'%')", nativeQuery = true)
    List<ReservaEntity> findReservasByNomeCliente(String nome);

}