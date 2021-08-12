package restaurante.gif.exceptions;

import org.springframework.validation.ObjectError;
import restaurante.gif.model.Restaurante;

import java.util.List;

public class RestauranteCadastradoException extends Exception {

    private Restaurante restaurante;
    private List<ObjectError> errors;

    public static RestauranteCadastradoException createWith(List<ObjectError> errors) {
        return new RestauranteCadastradoException(errors);
    }

    public RestauranteCadastradoException(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    private RestauranteCadastradoException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

}
