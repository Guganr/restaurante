package restaurante.gif.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import restaurante.gif.exceptions.CNPJInvalidoException;

import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
public class RestauranteServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestauranteService restauranteService;

    @Before
    public void setUp(){
        this.restauranteService = mock(RestauranteService.class);
    }


    public void validaCNPJError() throws CNPJInvalidoException {
        Assert.assertFalse(this.restauranteService.validaCNPJ("452125413294212"));
    }


}