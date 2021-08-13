package restaurante.gif.repository;

import org.springframework.data.repository.CrudRepository;
import restaurante.gif.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(String idUsuario);
}
