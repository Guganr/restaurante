package restaurante.gif.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
@Data
@Document(collection = "restaurante")
@Entity
public class Restaurante implements Serializable {
    @Id
    private String id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String endereco;
    private List<CategoriaComida> categoriaComida;

}

