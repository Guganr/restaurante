package restaurante.gif.dto;

import lombok.Data;
import restaurante.gif.model.CategoriaComida;
import restaurante.gif.model.Restaurante;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RestauranteDTO {
    private String id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private String endereco;
    private List<CategoriaComida> categoriaComida;

}
