package restaurante.gif.exceptions;

public class EntidadeInexistenteException extends Exception{

    private String object;

    public EntidadeInexistenteException(String object) {
        this.object = object;
    }

    @Override
    public String getMessage() {
        return "Restaurante" + object + " não encontrado.";
    }
}
