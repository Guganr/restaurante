package restaurante.gif.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import restaurante.gif.exceptions.EmailInvalidoException;
import restaurante.gif.exceptions.RestauranteCadastradaException;
import restaurante.gif.exceptions.RestauranteInexistenteException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RestauranteServiceTest {


    @InjectMocks
    private RestauranteService restauranteService;
    @Mock
    private RestauranteRepository restauranteRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = EmailInvalidoException.class)
    public void validaEmailErro() {
        String emailInvalido = "aaabbbccc";
        assertFalse(restauranteService.validaEmail(emailInvalido));
    }

    @Test
    public void validaEmailSucesso() {
        String emailValido = "aaa@gmail.com";
        assertTrue(restauranteService.validaEmail(emailValido));
    }

    @Test(expected = RestauranteCadastradaException.class)
    public void validaEntidadeCadastradaSucesso() {
        Restaurante restaurante = new Restaurante();
        restaurante.setCnpj("21312312312");
        Optional<Restaurante> optionalRestaurante = Optional.of(new Restaurante("NOME", "CNPJ12345") );
        doReturn(optionalRestaurante).when(restauranteRepository).findByCnpj(restaurante.getCnpj());
        when(restauranteService.verificaSeRestauranteExiste(restaurante)).thenReturn(false);
    }

    @Test
    public void validaEntidadeCadastradaErro() {
        Restaurante restaurante =  mock(Restaurante.class);
        assertTrue(this.restauranteService.verificaSeRestauranteExiste(restaurante));
    }

    @Test
    public void getEntidadeSucesso() {
        Restaurante restaurante = mock(Restaurante.class);
        Optional<Restaurante> optionalRestaurante = Optional.ofNullable(restaurante);
        assertEquals(restaurante, restauranteService.getRestaurante("1", optionalRestaurante).get());
    }

    @Test(expected = RestauranteInexistenteException.class)
    public void getEntidadeErro() {
        restauranteService.getRestaurante("1", Optional.empty());
    }

}