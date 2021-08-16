package restaurante.gif.exceptions;

import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import restaurante.gif.model.Restaurante;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

public class EntidadeCadastradaException extends Exception {

    private Serializable model;
    private List<ObjectError> errors;

    public static EntidadeCadastradaException createWith(List<ObjectError> errors) {
        return new EntidadeCadastradaException(errors);
    }

    public EntidadeCadastradaException(Serializable model) {
        this.model = model;
    }

    private EntidadeCadastradaException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

}
