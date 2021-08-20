package restaurante.gif.exceptions;

import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.List;

public class RestauranteCadastradaException extends RuntimeException {

    private Serializable model;
    private List<ObjectError> errors;

    public static RestauranteCadastradaException createWith(List<ObjectError> errors) {
        return new RestauranteCadastradaException(errors);
    }

    public RestauranteCadastradaException(Serializable model) {
        this.model = model;
    }

    private RestauranteCadastradaException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return "Restaurante" + model.toString() + " já foi cadastrado.";
    }
}
