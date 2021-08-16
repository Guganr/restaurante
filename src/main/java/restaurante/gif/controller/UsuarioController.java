package restaurante.gif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante.gif.exceptions.EmailInvalidoException;
import restaurante.gif.exceptions.EntidadeCadastradaException;
import restaurante.gif.exceptions.EntidadeInexistenteException;
import restaurante.gif.exceptions.errors.ApiError;
import restaurante.gif.model.Restaurante;
import restaurante.gif.model.Usuario;
import restaurante.gif.service.UsuarioService;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Iterable<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Restaurante> listaUsuarioPorId(@PathVariable String id) {
        try {
            return new ResponseEntity(usuarioService.listaUsuarioPorId(id), HttpStatus.OK);
        } catch (EntidadeInexistenteException exception) {
            return new ResponseEntity(new ApiError(HttpStatus.CONFLICT, exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/email/{email}")
    public ResponseEntity<Usuario> listaUsuarioPorEmail(@PathVariable String email) {
        try{
            return new ResponseEntity(usuarioService.listaUsuarioPorEmail(email), HttpStatus.OK);
        }
        catch(EmailInvalidoException | EntidadeInexistenteException exception ){
            return new ResponseEntity(new ApiError(HttpStatus.CONFLICT, exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Usuario> salvaRestaurante(@RequestBody Usuario usuario)  {
        try {
            usuarioService.salvaUsuario(usuario);
        } catch (EntidadeCadastradaException | EmailInvalidoException exception) {
            return new ResponseEntity(new ApiError(HttpStatus.CONFLICT, exception), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizaUsuario(@PathVariable String id,
                                                                    @RequestBody Usuario usuario) {
        Optional<Usuario> oldUsuario = usuarioService.atualizaUsuario(id, usuario);
        if (oldUsuario.isPresent())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizaPrivilegioPorUsuario(@PathVariable String id,
                                                                    @RequestBody Usuario usuario) {
        Optional<Usuario> oldUsuario = usuarioService.atualizaPrivilegioPorUsuario(id, usuario);
        if (oldUsuario.isPresent())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizaSenhaPorUsuario(@PathVariable String id,
                                                                    @RequestBody Usuario usuario) {
        Optional<Usuario> oldUsuario = usuarioService.atualizaSenhaPorUsuario(id, usuario);
        if (oldUsuario.isPresent())
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletaUsuario(@PathVariable String id) {
        usuarioService.deletaUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
