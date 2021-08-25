package restaurante.gif.builder;

import restaurante.gif.model.CategoriaComida;
import restaurante.gif.model.Restaurante;

import java.util.List;

public class RestauranteBuilder {

    private String id;
    private String nome;
    private String cnpj;
    private String email;
    private String endereco;
    private List<CategoriaComida> categoriaComida;

    public RestauranteBuilder addNome(String nome) {
        this.nome = nome;
        return this;
    }

    public RestauranteBuilder addCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public RestauranteBuilder addEmail(String email) {
        this.email = email;
        return this;
    }

    public RestauranteBuilder addEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public RestauranteBuilder addCategoriaComida(List<CategoriaComida> categoriaComida) {
        this.categoriaComida = categoriaComida;
        return this;
    }

    public Restaurante build() { return new Restaurante(nome, cnpj, email, endereco, categoriaComida);}
}
