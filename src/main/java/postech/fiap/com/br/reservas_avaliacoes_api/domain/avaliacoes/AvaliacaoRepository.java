package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {

    @Query("SELECT COUNT(av.idavaliacao) > 0 FROM AvaliacaoEntity av WHERE av.idcliente= :idcliente AND av.idrestaurante = :idrestaurante AND date(av.dataavaliacao) = :dataavaliacao")
    boolean findByid_clienteAndid_restauranteAnddata_avaliacao(Long idcliente, Long idrestaurante, LocalDate dataavaliacao);


    @Query(value = "SELECT r.nome, COUNT(a.idavaliacao) AS total_avaliacoes, AVG(a.avaliacao) AS media_avaliacao " +
            "FROM avaliacoes a JOIN restaurantes r ON a.idrestaurante = r.idrestaurante " +
            "WHERE a.idrestaurante = :idRestaurante AND a.dataavaliacao >= NOW() - INTERVAL '30 day' " +
            "GROUP BY r.idrestaurante", nativeQuery = true)
    List<Object[]> getEstatisticasRestauranteUltimos30Dias(Long idRestaurante);

    @Query(value = "SELECT r.nome, COUNT(a.idavaliacao) AS total_avaliacoes, AVG(a.avaliacao) AS media_avaliacao " +
            "FROM avaliacoes a JOIN restaurantes r ON a.idrestaurante = r.idrestaurante " +
            "WHERE a.dataavaliacao >= NOW() - INTERVAL '30 day' " +
            "GROUP BY r.idrestaurante", nativeQuery = true)
    List<Object[]> getEstatisticasRestauranteUltimos30DiasTodos();

}
