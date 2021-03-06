package restaurante.gif.service;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.interfaces.Check;
import br.com.safeguard.types.ParametroTipo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurante.gif.exceptions.CNPJInvalidoException;
import restaurante.gif.exceptions.EmailInvalidoException;
import restaurante.gif.exceptions.RestauranteCadastradaException;
import restaurante.gif.exceptions.RestauranteInexistenteException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante salvaRestaurante(Restaurante restaurante) {
        if (validaInformacoesDeCadastro(restaurante)) {
            restauranteRepository.save(restaurante);
        }
        return restaurante;
    }

    public boolean verificaSeRestauranteExiste(Restaurante restaurante) {
        Optional<Restaurante> restauranteCheck = restauranteRepository.findByCnpj(restaurante.getCnpj());
        if (restauranteCheck.isPresent())
            throw new RestauranteCadastradaException(restaurante);
        else
            return true;
    }

    private boolean validaInformacoesDeCadastro(Restaurante restaurante) {
        return validaCNPJ(restaurante.getCnpj()) && verificaSeRestauranteExiste(restaurante) && validaEmail(restaurante.getEmail());
    }

    public boolean validaEmail(String email) {
        if (EmailValidator.getInstance().isValid(email))
            return true;
        else
            throw new EmailInvalidoException(email);
    }

    public Optional<Restaurante> listaRestaurantePorId(String id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return getRestaurante(id, restaurante);
    }

    private boolean validaCNPJ(String cnpj) {
        Check check = new SafeguardCheck();
        boolean validacao = check
                .elementOf(cnpj, ParametroTipo.CNPJ)
                .validate()
                .hasError();
        if (validacao)
            throw new CNPJInvalidoException(cnpj);
        return true;
    }

    public Optional<Restaurante> atualizaRestaurantePorId(String id, Restaurante novoRestaurante) {
        if (novoRestaurante.getCnpj() != null)
            validaCNPJ(novoRestaurante.getCnpj());
        if (novoRestaurante.getEmail() != null)
            validaEmail(novoRestaurante.getEmail());

        return restauranteRepository.findById(id)
                .map(restaurante -> {
                    if (novoRestaurante.getNome() != null)
                        restaurante.setNome(novoRestaurante.getNome());
                    if (novoRestaurante.getCnpj() != null)
                    restaurante.setCnpj(novoRestaurante.getCnpj());
                    if (novoRestaurante.getEmail() != null)
                    restaurante.setEmail(novoRestaurante.getEmail());
                    if (novoRestaurante.getEndereco() != null)
                    restaurante.setEndereco(novoRestaurante.getEndereco());
                    return restauranteRepository.save(restaurante);
                });

    }

    public void deletaRestaurante(String id) {
        restauranteRepository.deleteById(id);
    }

    public Optional<Restaurante> listaRestaurantePorCnpj(String cnpj) {
        if (validaCNPJ(cnpj)) {
            Optional<Restaurante> restaurante = restauranteRepository.findByCnpj(cnpj);
            return getRestaurante(cnpj, restaurante);
        } else
            throw new CNPJInvalidoException(cnpj);
    }

    public List<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> getRestaurante(String id, Optional<Restaurante> model) {
        if (model.isEmpty())
            throw new RestauranteInexistenteException(id);
        return model;
    }
}
