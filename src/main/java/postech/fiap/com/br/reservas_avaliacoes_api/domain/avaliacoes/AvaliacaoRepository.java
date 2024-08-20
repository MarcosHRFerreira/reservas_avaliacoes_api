package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {

    @Query("SELECT COUNT(av) > 0 FROM AvaliacaoEntity av WHERE av.ID_cliente= :id_cliente AND av.ID_restaurante = :id_restaurante AND date(av.data_avaliacao) = :data_avaliacao")
    boolean findByid_clienteAndid_restauranteAnddata_avaliacao(Long id_cliente, Long id_restaurante, LocalDate data_avaliacao);


}
