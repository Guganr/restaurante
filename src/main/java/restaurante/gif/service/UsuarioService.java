package restaurante.gif.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import restaurante.gif.exceptions.EmailInvalidoException;
import restaurante.gif.exceptions.EntidadeCadastradaException;
import restaurante.gif.exceptions.EntidadeInexistenteException;
import restaurante.gif.model.Usuario;
import restaurante.gif.repository.UsuarioRepository;
import restaurante.gif.service.commons.ServiceCommons;

import java.util.Optional;

@Service
public class UsuarioService extends ServiceCommons {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> atualizaPrivilegioPorUsuario(String id, Usuario newUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setPerfis(newUsuario.getPerfis());
                    return usuarioRepository.save(usuario);
                });

    }

    public void salvaUsuario(Usuario usuario) throws EmailInvalidoException, EntidadeCadastradaException {
        if (validaEmail(usuario.getEmail())) {
            if (verificaSeUsuarioExiste(usuario)){
                usuarioRepository.save(usuario);
            }
        }
    }

    private boolean validaEmail(String email) throws EmailInvalidoException {
        if (EmailValidator.getInstance().isValid(email))
            return true;
        else
            throw new EmailInvalidoException(email);
    }

    private boolean verificaSeUsuarioExiste(Usuario usuario) throws EntidadeCadastradaException {
        Optional<Usuario> usuarioCheck = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioCheck.isPresent())
            throw new EntidadeCadastradaException(usuario);
        else
            return true;
    }

    public Optional<?> listaUsuarioPorEmail(String email) throws EmailInvalidoException, EntidadeInexistenteException {
        if (validaEmail(email)) {
            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
            return getEntidade(email, usuario);
        } else
            return Optional.empty();
    }

    public Optional<?> listaUsuarioPorId(String id) throws EntidadeInexistenteException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return getEntidade(id, usuario);
    }

    public Optional<Usuario> atualizaSenhaPorUsuario(String id, Usuario newUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setSenha(new BCryptPasswordEncoder().encode(newUsuario.getSenha()));
                    return usuarioRepository.save(usuario);
                });
    }


    public void deletaUsuario(String id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Usuario> atualizaUsuario(String id, Usuario newUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(newUsuario.getNome());
                    usuario.setPerfis(newUsuario.getPerfis());
                    usuario.setSenha(newUsuario.getSenha());
                    usuario.setEmail(newUsuario.getEmail());
                    return usuarioRepository.save(usuario);
                });
    }
}
