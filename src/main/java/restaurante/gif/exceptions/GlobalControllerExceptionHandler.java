package restaurante.gif.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import restaurante.gif.exceptions.errors.ApiError;
import restaurante.gif.model.Restaurante;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CNPJInvalidoException.class)
    public ResponseEntity<ApiError> handleCNPJInvalidoException(CNPJInvalidoException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestauranteInexistenteException.class)
    public ResponseEntity<ApiError> handleRestauranteInexistenteException(RestauranteInexistenteException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestauranteCadastradaException.class)
    public ResponseEntity<ApiError> handleRestauranteCadastradaException(RestauranteCadastradaException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

}
