package restaurante.gif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante.gif.model.Restaurante;
import restaurante.gif.service.RestauranteService;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @RequestMapping(method = GET)
    public Iterable<Restaurante> listarRestaurantes() {
        return restauranteService.findAll();
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Restaurante> salvaRestaurante(@RequestBody Restaurante restaurante)  {
        restauranteService.salvaRestaurante(restaurante);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/{id}")
    public ResponseEntity<Restaurante> listaRestaurantePorId(@PathVariable String id) {
        return new ResponseEntity(restauranteService.listaRestaurantePorId(id), HttpStatus.OK);
    }

    @RequestMapping("/cnpj/{cnpj}")
    public ResponseEntity<Restaurante> listaRestaurantePorCnpj(@PathVariable String cnpj) {
        return new ResponseEntity(restauranteService.listaRestaurantePorCnpj(cnpj), HttpStatus.OK);
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
