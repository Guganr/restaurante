package restaurante.gif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante.gif.exceptions.errors.ApiError;
import restaurante.gif.exceptions.CNPJInvalidoException;
import restaurante.gif.exceptions.RestauranteCadastradoException;
import restaurante.gif.model.Restaurante;
import restaurante.gif.repository.RestauranteRepository;
import restaurante.gif.service.RestauranteService;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private RestauranteService restauranteService;

    @RequestMapping(method = GET)
    public Iterable<Restaurante> listarRestaurantes() {
        return restauranteRepository.findAll();
    }

    @RequestMapping(method = POST)
    @ExceptionHandler(CNPJInvalidoException.class)
    public ResponseEntity<Restaurante> salvaRestaurante(@RequestBody Restaurante restaurante)  {
        try {
            restauranteService.salvaRestaurante(restaurante);
        } catch (CNPJInvalidoException | RestauranteCadastradoException exception) {
            return new ResponseEntity(new ApiError(HttpStatus.CONFLICT, exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/{id}")
    public ResponseEntity<Restaurante> listaRestaurantePorId(@PathVariable String id) {
        Optional<Restaurante> restaurante = restauranteService.listaRestaurantePorId(id);
        return restaurante.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping("/cnpj/{cnpj}")
    public ResponseEntity<Restaurante> listaRestaurantePorCnpj(@PathVariable String cnpj) {
        try{
            return new ResponseEntity(restauranteService.listaRestaurantePorCnpj(cnpj), HttpStatus.OK);
        }
        catch(CNPJInvalidoException cnpjInvalidoException ){
            return new ResponseEntity(new ApiError(HttpStatus.CONFLICT, cnpjInvalidoException), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/cnpje/{cnpj}")
    public ResponseEntity<Restaurante> listaRestaurantePorCnpje(@PathVariable String cnpj) {
        Optional<Restaurante> restaurante = restauranteService.listaRestaurantePorCnpje(cnpj);

        var headers = new HttpHeaders();
        headers.add("Responded", "RestauranteController");
        return  ResponseEntity.accepted().headers(headers).body(restaurante.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> editaRestaurante(@PathVariable String id, @RequestBody Restaurante restaurante)
    {
        Optional<Restaurante> oldRestaurante = restauranteService.atualizaRestaurantePorId(id, restaurante);
        if (oldRestaurante.isPresent())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurante> deletaRestaurante(@PathVariable String id) {
        restauranteService.deletaRestaurante(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
