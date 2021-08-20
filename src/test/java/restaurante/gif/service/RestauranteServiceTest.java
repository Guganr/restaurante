package restaurante.gif.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import restaurante.gif.exceptions.CNPJInvalidoException;
import restaurante.gif.exceptions.RestauranteCadastradaException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class RestauranteServiceTest {


    @InjectMocks
    private RestauranteService restauranteService;
    @Mock
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
//        this.restauranteService = mock(RestauranteService.class);
//        this.restauranteRepository = mock(RestauranteRepository.class);
    }


    public void validaCNPJError() throws CNPJInvalidoException {
        Assert.assertFalse(this.restauranteService.validaCNPJ("452125413294212"));
    }

    @Test
    public void validaEntidadeCadastradaError() throws RestauranteCadastradaException {
        Restaurante restaurante = new Restaurante();
        restaurante.setCnpj("21312312312");
        Optional<Restaurante> optionalRestaurante = Optional.of(restaurante);
//        when(restauranteRepository.findByCnpj(anyString())).thenReturn(optionalRestaurante);
        doReturn(optionalRestaurante).when(restauranteRepository).findByCnpj(restaurante.getCnpj());
        restauranteService.verificaSeRestauranteExisteTeste(restaurante);
//        Assert.assertFalse(this.restauranteService.verificaSeRestauranteExisteTeste(restaurante));
    }


}