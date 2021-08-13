package restaurante.gif.service;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.exceptions.SafeguardException;
import br.com.safeguard.interfaces.Check;
import br.com.safeguard.types.ParametroTipo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.gif.exceptions.CNPJInvalidoException;
import restaurante.gif.exceptions.EmailInvalidoException;
import restaurante.gif.exceptions.RestauranteCadastradoException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;

import java.util.Optional;

@Service
public class RestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;
    private SafeguardException  SafeguardException;

    public Restaurante salvaRestaurante(Restaurante restaurante) throws CNPJInvalidoException, RestauranteCadastradoException, EmailInvalidoException {
        if (validaInformacoesDeCadastro(restaurante)) {
            restaurante.getId();
            restauranteRepository.save(restaurante);
        }
        return restaurante;
    }

    private boolean verificaSeRestauranteExiste(Restaurante restaurante) throws RestauranteCadastradoException {
        Optional<Restaurante> restauranteCheck = restauranteRepository.findByCnpj(restaurante.getCnpj());
        if (restauranteCheck.isPresent())
            throw new RestauranteCadastradoException(restaurante);
        else
            return true;
    }

    private boolean validaInformacoesDeCadastro(Restaurante restaurante) throws RestauranteCadastradoException, CNPJInvalidoException, EmailInvalidoException {
        return validaCNPJ(restaurante.getCnpj()) && verificaSeRestauranteExiste(restaurante) && validaEmail(restaurante.getEmail());
    }

    private boolean validaEmail(String email) throws EmailInvalidoException {
        if (EmailValidator.getInstance().isValid(email))
            return true;
        else
            throw new EmailInvalidoException(email);
    }

    public Optional<Restaurante> listaRestaurantePorId(String id) {
        return restauranteRepository.findById(id);
    }

    public boolean validaCNPJ(String cnpj) throws CNPJInvalidoException {
        Check check = new SafeguardCheck();
        boolean validacao = check
                .elementOf(cnpj, ParametroTipo.CNPJ)
                .validate()
                .hasError();
        if (validacao)
            throw new CNPJInvalidoException(cnpj);
        return true;
    }

    public Optional<Restaurante> atualizaRestaurantePorId(String id, Restaurante newRestaurante) {
        return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restaurante.setNome(newRestaurante.getNome());
                    restaurante.setCnpj(newRestaurante.getCnpj());
                    restaurante.setEndereco(newRestaurante.getEndereco());
                    return restauranteRepository.save(restaurante);
                });

    }

    public void deletaRestaurante(String id) {
        restauranteRepository.deleteById(id);
    }

    public Optional<Restaurante> listaRestaurantePorCnpj(String cnpj) throws CNPJInvalidoException {
        if (validaCNPJ(cnpj))
            return restauranteRepository.findByCnpj(cnpj);
        else
            throw new CNPJInvalidoException(cnpj);
    }

    public Optional<Restaurante> listaRestaurantePorCnpje(String cnpj) {
        return restauranteRepository.findByCnpj(cnpj);
    }

    public Iterable<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }
}
