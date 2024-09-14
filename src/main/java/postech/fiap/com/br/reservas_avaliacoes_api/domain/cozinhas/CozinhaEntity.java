package postech.fiap.com.br.reservas_avaliacoes_api.domain.cozinhas;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor

@Table(name = "cozinhas")
@Entity(name = "CozinhaEntity")

public class CozinhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idcozinha;

    @NotNull
    @NotBlank(message = "A especialidade é obrigatória")
    @Size(min = 1, max=200, message = "O nome do bairro deve ter no mínimo 3 caracteres e no maxímo 200")
    private String especialidade;

    public CozinhaEntity() {
        //
    }
    public Long getIdcozinha() {
        return idcozinha;
    }
    public void setIdcozinha(Long idcozinha) {
        this.idcozinha = idcozinha;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CozinhaEntity cozinha = (CozinhaEntity) o;
        return Objects.equals(idcozinha, cozinha.idcozinha);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(idcozinha);
    }
    @Override
    public String toString() {
        return "CozinhaEntity{" +
                "idcozinha=" + idcozinha +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
    public void atualizarInformacoes(DadosAtualizacaoCozinhaDto dados) {

        if (dados.especialidade() != null) {
            this.especialidade = dados.especialidade().toUpperCase();
        }
    }

}
