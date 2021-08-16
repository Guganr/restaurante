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
import restaurante.gif.exceptions.EntidadeCadastradaException;
import restaurante.gif.exceptions.EntidadeInexistenteException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;
import restaurante.gif.service.commons.ServiceCommons;

import java.util.Optional;

@Service
public class RestauranteService extends ServiceCommons {

    @Autowired
    private RestauranteRepository restauranteRepository;
    private SafeguardException  SafeguardException;

    public Restaurante salvaRestaurante(Restaurante restaurante) throws CNPJInvalidoException, EntidadeCadastradaException, EmailInvalidoException {
        if (validaInformacoesDeCadastro(restaurante)) {
            restaurante.getId();
            restauranteRepository.save(restaurante);
        }
        return restaurante;
    }

    private boolean verificaSeRestauranteExiste(Restaurante restaurante) throws EntidadeCadastradaException {
        Optional<Restaurante> restauranteCheck = restauranteRepository.findByCnpj(restaurante.getCnpj());
        if (restauranteCheck.isPresent())
            throw new EntidadeCadastradaException(restaurante);
        else
            return true;
    }

    private boolean validaInformacoesDeCadastro(Restaurante restaurante) throws EntidadeCadastradaException, CNPJInvalidoException, EmailInvalidoException {
        return validaCNPJ(restaurante.getCnpj()) && verificaSeRestauranteExiste(restaurante) && validaEmail(restaurante.getEmail());
    }

    private boolean validaEmail(String email) throws EmailInvalidoException {
        if (EmailValidator.getInstance().isValid(email))
            return true;
        else
            throw new EmailInvalidoException(email);
    }

    public Optional<?> listaRestaurantePorId(String id) throws EntidadeInexistenteException {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return getEntidade(id, restaurante);
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
                    restaurante.setCategoriaComida(newRestaurante.getCategoriaComida());
                    return restauranteRepository.save(restaurante);
                });

    }

    public void deletaRestaurante(String id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<?> listaRestaurantePorCnpj(String cnpj) throws CNPJInvalidoException, EntidadeInexistenteException {
        if (validaCNPJ(cnpj)) {
            Optional<Restaurante> restaurante = restauranteRepository.findByCnpj(cnpj);
            return getEntidade(cnpj, restaurante);
        } else
            throw new CNPJInvalidoException(cnpj);
    }

    public Iterable<Restaurante> findAll() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> atualizaEndederecoRestaurante(String id, Restaurante newRestaurante) {
        return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restaurante.setEndereco(newRestaurante.getEndereco());
                    return restauranteRepository.save(restaurante);
                });
    }
    public Optional<Restaurante> atualizaCategoriaRestaurante(String id, Restaurante newRestaurante) {
        return restauranteRepository.findById(id)
                .map(restaurante -> {
                    restaurante.setCategoriaComida(newRestaurante.getCategoriaComida());
                    return restauranteRepository.save(restaurante);
                });
    }
}
