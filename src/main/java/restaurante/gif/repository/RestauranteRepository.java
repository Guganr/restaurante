package restaurante.gif.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import restaurante.gif.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends MongoRepository<Restaurante, String> {

    List<Restaurante> findByNomeContaining(String nome);
    Optional<Restaurante> findByCnpj(String cnpj);
    List<Restaurante> findByEmailContaining(String email);

}
