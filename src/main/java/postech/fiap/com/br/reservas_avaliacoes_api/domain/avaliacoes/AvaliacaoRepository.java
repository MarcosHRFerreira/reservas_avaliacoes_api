package postech.fiap.com.br.reservas_avaliacoes_api.domain.avaliacoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {

    @Query("SELECT COUNT(av.idavaliacao) > 0 FROM AvaliacaoEntity av WHERE av.idcliente= :idcliente AND av.idrestaurante = :idrestaurante AND date(av.dataavaliacao) = :dataavaliacao")
    boolean findByid_clienteAndid_restauranteAnddata_avaliacao(Long idcliente, Long idrestaurante, LocalDate dataavaliacao);


}
