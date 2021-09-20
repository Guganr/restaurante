package restaurante.gif.exceptions;

public class RestauranteInexistenteException extends RuntimeException{

    private String object;

    public RestauranteInexistenteException(String object) {
        this.object = object;
    }

    @Override
    public String getMessage() {
        return "Restaurante " + object + " não encontrado.";
    }
}
