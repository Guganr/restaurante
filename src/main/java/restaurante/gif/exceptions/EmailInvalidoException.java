package restaurante.gif.exceptions;

public class EmailInvalidoException extends RuntimeException {

    private String email;

    public EmailInvalidoException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return email + " inválido.";
    }
}
