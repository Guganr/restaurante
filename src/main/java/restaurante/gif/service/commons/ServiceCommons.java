package restaurante.gif.service.commons;

import org.springframework.stereotype.Service;
import restaurante.gif.exceptions.EntidadeInexistenteException;
import restaurante.gif.model.Restaurante;

import java.io.Serializable;
import java.util.Optional;

public class ServiceCommons {

    public Optional<?> getEntidade(String id, Optional<?> model) throws EntidadeInexistenteException {
        if (model.isEmpty())
            throw new EntidadeInexistenteException(id);
        return model;
    }
}
