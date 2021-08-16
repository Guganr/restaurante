package restaurante.gif.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Document(collection = "restaurante")
@Entity
public class Restaurante implements Serializable {
    @Id
    private String id;
    private String nome;
    @Column(name = "cnpj")
    private String cnpj;
    private String email;
    private String senha;
    private String endereco;
    private List<CategoriaComida> categoriaComida;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<CategoriaComida> getCategoriaComida() {
        return categoriaComida;
    }

    public void setCategoriaComida(List<CategoriaComida> categoriaComida) {
        this.categoriaComida = categoriaComida;
    }
}

