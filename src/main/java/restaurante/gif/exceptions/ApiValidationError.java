package restaurante.gif.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import restaurante.gif.exceptions.errors.ApiSubError;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public
class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
