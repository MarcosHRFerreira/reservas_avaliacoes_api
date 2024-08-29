package postech.fiap.com.br.reservas_avaliacoes_api.domain.restaurantes;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Table(name = "restaurantes")
@Entity(name = "RestauranteEntity")

@Getter
@Setter
@AllArgsConstructor

public class RestauranteEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idrestaurante;

    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Size(min = 3,max=100, message = "O nome do restaurante deve ter no mínimo 3 caracteres e no maxímo 100")
    private String nome;

    @Email(message = "O email deve ter um formato válido")
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "O telefone deve ter 10 ou 11 dígitos númericos")
    private String telefone;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 3, max=100, message = "O nome do logradouro deve ter no mínimo 3 caracteres e no maxímo 100")
    private String logradouro;

    @NotBlank(message = "O nome do bairro é obrigatório")
    @Size(min = 3, max=100, message = "O nome do bairro deve ter no mínimo 3 caracteres e no maxímo 100")
    private String bairro;

    @Pattern(regexp = "^\\d{8}$", message = "O CEP deve ter 8 dígitos númericos")
    private String cep;

    @Size(max=100, message = "O nome do complemento deve ter no maxímo 100")
    private String complemento;

    @NotBlank(message = "O número do logradouro é obrigatório")
    @Size(min = 1, max=20, message = "O número do logradouro deve ter no mínimo 1 caracteres e no maxímo 20")
    private String numero;

    @NotBlank(message = "A UF é obrigatório")
    @Size(min = 2, max=2, message = "A UF deve ter no mínimo 2 caracteres e no maxímo 2")
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "A UF deve conter apenas letras")
    private String uf;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 1, max=100, message = "A cidade deve ter no mínimo 1 caracteres e no maxímo 100")
    private String cidade;


    public Long getIdrestaurante() {
        return idrestaurante;
    }

    public void setIdrestaurante(Long idrestaurante) {
        this.idrestaurante = idrestaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "RestauranteEntity{" +
                "idrestaurante=" + idrestaurante +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                ", numero='" + numero + '\'' +
                ", uf='" + uf + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestauranteEntity that = (RestauranteEntity) o;
        return Objects.equals(idrestaurante, that.idrestaurante);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idrestaurante);
    }

    public RestauranteEntity() {
        //
    }

    public void atualizarInformacoes(DadosAtualizacaoRestauranteDto dados){

        if(dados.nome() != null ){
            this.nome=dados.nome().toUpperCase();
        }
        if(dados.email() != null){
            this.email=dados.email().toUpperCase();
        }
        if(dados.telefone() != null){
            this.email=telefone;
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



