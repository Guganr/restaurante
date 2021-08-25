package restaurante.gif.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import restaurante.gif.dto.RestauranteDTO;
import restaurante.gif.model.Restaurante;
import restaurante.gif.service.RestauranteService;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestauranteService service;

    @Autowired
    private RestauranteController controller;



    @Test
    public void salvaRestaurante() throws Exception {

        Restaurante restaurante = mock(Restaurante.class);
        URI uri = new URI("/restaurante");
        String json = "{\"email\":\"restaurante@email.com\",\"nome\":\"restaurante\"}";
        given(service.salvaRestaurante(any())).willReturn(restaurante);
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201))
                .andReturn()
                .getResponse();
    }

    @Test
    public void listaRestaurantePorIdSucesso() throws Exception {

        Restaurante restaurante = mock(Restaurante.class);
        Optional<Restaurante> optionalRestaurante = Optional.ofNullable(restaurante);
        URI uri = new URI("/restaurante/1");
        given(service.listaRestaurantePorId(any())).willReturn(optionalRestaurante);
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200))
                .andReturn()
                .getResponse();
    }

    @Test
    public void listaRestaurantePorIdErro() throws Exception {

        URI uri = new URI("/restaurante/1");
        given(service.listaRestaurantePorId(any())).willReturn(Optional.empty());
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(404))
                .andReturn()
                .getResponse();
    }

    @Test
    public void listaRestaurantePorCnpjSucesso() throws Exception {

        Restaurante restaurante = mock(Restaurante.class);
        Optional<Restaurante> optionalRestaurante = Optional.ofNullable(restaurante);
        URI uri = new URI("/restaurante/cnpj/111111111111");
        given(service.listaRestaurantePorCnpj(any())).willReturn(optionalRestaurante);
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .param("cnpj", "111111111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200))
                .andReturn()
                .getResponse();
    }

    @Test
    public void listaRestaurantePorCnpjErro() throws Exception {

        URI uri = new URI("/restaurante/cnpj/111111111111");
        given(service.listaRestaurantePorCnpj(any())).willReturn(Optional.empty());
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .param("cnpj", "111111111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(404))
                .andReturn()
                .getResponse();
    }

}