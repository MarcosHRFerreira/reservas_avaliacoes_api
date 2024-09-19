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

    List<RestauranteEntity> findByUFContaining(String UF);

    @Query(value = "SELECT  r.idrestaurante, r.nome , r.email, r.telefone,  r.logradouro, " +
    "r.bairro, r.cep, r.complemento, r.numero, r.uf, r.cidade, r.funcionamento, r.capacidade, c.especialidade " +
    " FROM Restaurantes r JOIN restaurantes_cozinhas rc ON r.idrestaurante = rc.idrestaurante  JOIN " +
    " Cozinhas c ON rc.idcozinha = c.idcozinha WHERE c.idcozinha =:codcozinha" ,nativeQuery = true)

    List<DadosDetalhamentoRestauranteEspecialidadeDto> findRestaurantesByCozinhaEspecialidadeContaining(Long codcozinha);
}
