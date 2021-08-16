package restaurante.gif.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
public class Perfil implements GrantedAuthority {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
