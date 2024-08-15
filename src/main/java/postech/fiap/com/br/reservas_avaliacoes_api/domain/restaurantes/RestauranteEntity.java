package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "restaurantes")
@Entity(name = "RestauranteEntity")

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class RestauranteEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_restaurante;


    private String nome;

    private String email;

    private String logradouro;

    private String bairro;

    private String cep;

    private String complemento;

    private String numero;

    private String uf;

    private String cidade;


    public void atualizarInformacoes(DadosAtualizacaoRestauranteDto dados){

        if(dados.nome() != null ){
            this.nome=dados.nome().toUpperCase();
        }

        if(dados.email() != null){
            this.email=dados.email().toUpperCase();
        }

        if(dados.logradouro() != null){
            this.logradouro=dados.logradouro().toUpperCase();
        }

        if(dados.bairro() != null){
            this.bairro=dados.bairro().toUpperCase();
        }

        if(dados.cep() != null){
            this.cep=dados.cep().toUpperCase();
        }

        if(dados.complemento() != null){
            this.complemento=dados.complemento().toUpperCase();
        }

        if(dados.numero() != null){
            this.numero=dados.numero().toUpperCase();
        }

        if(dados.uf() != null){
            this.uf=dados.uf().toUpperCase();
        }

        if(dados.cidade() != null){
            this.cidade=dados.cidade().toUpperCase();
        }

    }

}



