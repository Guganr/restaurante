package restaurante.gif.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import restaurante.gif.exceptions.CNPJInvalidoException;

import static org.mockito.Mockito.mock;

class RestauranteServiceTest {

    private RestauranteService restauranteService;

    @Test
    public void teste() throws CNPJInvalidoException {
        restauranteService = mock(RestauranteService.class);
        Assert.assertFalse(restauranteService.validaCNPJ("452125413294212"));
    }


}