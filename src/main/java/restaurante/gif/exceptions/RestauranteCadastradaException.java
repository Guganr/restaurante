package restaurante.gif.exceptions;

import org.springframework.validation.ObjectError;
import restaurante.gif.model.Restaurante;

import java.io.Serializable;
import java.util.List;

public class RestauranteCadastradaException extends RuntimeException {

    private Restaurante restaurante;
    private List<ObjectError> errors;

    public static RestauranteCadastradaException createWith(List<ObjectError> errors) {
        return new RestauranteCadastradaException(errors);
    }

    public RestauranteCadastradaException() {}

    public RestauranteCadastradaException(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    private RestauranteCadastradaException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return "Restaurante " + restaurante.getNome() + " já foi cadastrado.";
    }
}
