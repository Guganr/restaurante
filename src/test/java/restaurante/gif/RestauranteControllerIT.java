package restaurante.gif;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import restaurante.gif.builder.RestauranteBuilder;
import restaurante.gif.model.CategoriaComida;
import restaurante.gif.model.Restaurante;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureDataMongo
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RestauranteControllerIT {

    private String id;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void addRestaurantes() {
        Restaurante restaurante1 = new RestauranteBuilder()
                .addNome("Lo Rosti")
                .addEmail("lorosti@gmail.com")
                .addCnpj("95455137000147")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(0, "Batatas")))
                .build();
        Restaurante restaurante2 = new RestauranteBuilder()
                .addNome("Mc Donalds")
                .addEmail("mcdonalds@gmail.com")
                .addCnpj("76407855000101")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(0, "Fast Food")))
                .build();
        Restaurante restaurante3 = new RestauranteBuilder()
                .addNome("Restaurante Santa Rosa")
                .addEmail("restaurantesantarosa@gmail.com")
                .addCnpj("66157526000198")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(0, "Carnes")))
                .build();
        this.id = mongoTemplate.save(restaurante1, "restaurante").getId();
        mongoTemplate.save(restaurante2, "restaurante");
        mongoTemplate.save(restaurante3, "restaurante");

    }

    @AfterEach
    public void resetaCollection() {
        mongoTemplate.dropCollection("restaurante");
    }

    @Test
    public void editaRestauranteSucesso() throws Exception {

        URI uri = new URI("/restaurante/" + id);
        Restaurante restaurante = new RestauranteBuilder()
                .addNome("Lo Rosti - batatas incríveis")
                .addEmail("lorostibatatasincriveis@gmail.com")
                .addEndereco("Rua qualquer, 456")
                .build();
        String restauranteJson = objectMapper.writeValueAsString(restaurante);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(restauranteJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201))
                .andReturn()
                .getResponse();


        Restaurante restauranteAtualizado = mongoTemplate.findById(id, Restaurante.class);
        Assertions.assertNotNull(restauranteAtualizado);
        Assertions.assertEquals(restaurante.getNome(), restauranteAtualizado.getNome());
        Assertions.assertEquals(restaurante.getEmail(), restauranteAtualizado.getEmail());
        Assertions.assertEquals(restaurante.getEndereco(), restauranteAtualizado.getEndereco());
    }

    @Test
    public void salvaRestauranteSucesso() throws Exception {

        URI uri = new URI("/restaurante");
        Restaurante restaurante = new RestauranteBuilder()
                .addNome("goku")
                .addEmail("goku@gmail.com")
                .addCnpj("63680344000109")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(1, "Bistrô")))
                .build();
        String restauranteJson = objectMapper.writeValueAsString(restaurante);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(restauranteJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201))
                .andReturn()
                .getResponse();

        assertThat(mongoTemplate.findAll(Restaurante.class, "restaurante")).extracting("nome")
                .contains("goku");
    }

    @Test
    public void salvaRestauranteErroCNPJ() throws Exception {

        URI uri = new URI("/restaurante");
        Restaurante restaurante = new RestauranteBuilder()
                .addNome("goku")
                .addEmail("goku@email.com")
                .addCnpj("63680344000129")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(1, "Bistrô")))
                .build();
        String restauranteJson = objectMapper.writeValueAsString(restaurante);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(restauranteJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400))
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("O CNPJ: 63680344000129Ã© invÃ¡lido.");
    }

    @Test
    public void salvaRestauranteErroEmail() throws Exception {

        URI uri = new URI("/restaurante");
        Restaurante restaurante = new RestauranteBuilder()
                .addNome("goku")
                .addEmail("goku@")
                .addCnpj("63680344000109")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(1, "Bistrô")))
                .build();
        String restauranteJson = objectMapper.writeValueAsString(restaurante);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(restauranteJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400))
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("goku@ invÃ¡lido.");
    }

    @Test
    public void salvaRestauranteErroDuplicado() throws Exception {

        URI uri = new URI("/restaurante");
        Restaurante restaurante = new RestauranteBuilder()
                .addNome("goku")
                .addEmail("goku@email.com")
                .addCnpj("63680344000109")
                .addEndereco("Rua qualquer, 123")
                .addCategoriaComida(Collections.singletonList(new CategoriaComida(1, "Bistrô")))
                .build();

        mongoTemplate.save(restaurante, "restaurante");
        String restauranteJson = objectMapper.writeValueAsString(restaurante);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(restauranteJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400))
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).contains("Restaurante goku jÃ¡ foi cadastrado.");
    }

    @Test
    public void listaTodosRestaurantes()  {
        List<Restaurante> restaurante = mongoTemplate.findAll(Restaurante.class, "restaurante");

        Assertions.assertNotNull(restaurante);
        Assertions.assertFalse(restaurante.isEmpty());
        Assertions.assertEquals(3, restaurante.size());
    }

    @Test
    public void listaRestaurantePorId()  {
        Restaurante restaurante = mongoTemplate.findById(id, Restaurante.class);

        Assertions.assertNotNull(restaurante);
        Assertions.assertEquals("Lo Rosti", restaurante.getNome());
    }

    @Test
    public void listaRestaurantePorCNPJ()  {
        Query query = new Query();
        query.addCriteria(Criteria.where("cnpj").is("95455137000147"));
        List<Restaurante> restaurante = mongoTemplate.find(query, Restaurante.class);

        Assertions.assertNotNull(restaurante);
        Assertions.assertFalse(restaurante.isEmpty());
        Assertions.assertEquals(1, restaurante.size());
        assertThat(restaurante).extracting("nome")
                .contains("Lo Rosti");
    }

    @Test
    public void deletaRestaurante() throws Exception {
        URI uri = new URI("/restaurante/" + id);

        MockHttpServletResponse response = mockMvc
            .perform(MockMvcRequestBuilders
                    .delete(uri)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                    .status()
                    .is(200))
            .andReturn()
            .getResponse();
        Restaurante restaurante = mongoTemplate.findById(id, Restaurante.class);

        Assertions.assertNull(restaurante);
    }


}