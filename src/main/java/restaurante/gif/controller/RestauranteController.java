package restaurante.gif.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante.gif.dto.RestauranteDTO;
import restaurante.gif.model.Restaurante;
import restaurante.gif.service.RestauranteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("restaurante")
public class RestauranteController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping()
    public List<RestauranteDTO> listarRestaurantes() {
        List<Restaurante> restaurantes = restauranteService.findAll();
        return restaurantes
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<RestauranteDTO> salvaRestaurante(@RequestBody RestauranteDTO restauranteDTO)  {
        Restaurante restaurante = converterParaEntity(restauranteDTO);
        Restaurante novoRestaurante = restauranteService.salvaRestaurante(restaurante);
        return new ResponseEntity<>(converterParaDTO(novoRestaurante), HttpStatus.CREATED);
    }


    @RequestMapping("/{id}")
    public ResponseEntity<RestauranteDTO> listaRestaurantePorId(@PathVariable String id) {
        return restauranteService.listaRestaurantePorId(id)
                .map(restaurante -> new ResponseEntity(converterParaDTO(restaurante), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @RequestMapping("/cnpj/{cnpj}")
    public ResponseEntity<Restaurante> listaRestaurantePorCnpj(@PathVariable String cnpj) {
        restauranteService.listaRestaurantePorCnpj(cnpj)
                .map(restaurante -> new ResponseEntity(converterParaDTO(restaurante), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> editaRestaurante(@PathVariable String id, @RequestBody RestauranteDTO restauranteDTO)
    {
        Restaurante restaurante = converterParaEntity(restauranteDTO);
        Optional<Restaurante> novoRestaurante = restauranteService.atualizaRestaurantePorId(id, restaurante);
        return new ResponseEntity<>(converterParaDTO(novoRestaurante.get()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurante> deletaRestaurante(@PathVariable String id) {
        restauranteService.deletaRestaurante(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public RestauranteDTO converterParaDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public Restaurante converterParaEntity(RestauranteDTO restauranteDTO) {
        Restaurante restaurante = modelMapper.map(restauranteDTO, Restaurante.class);
        if (restaurante.getId() != null) {
            Optional<Restaurante> optionalRestaurante = restauranteService.listaRestaurantePorId(restaurante.getId());
            if (optionalRestaurante.isPresent())
                return optionalRestaurante.get();
        }
        return restaurante;
    }
}
