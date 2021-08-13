package restaurante.gif.config.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Token {

    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String tipo;
}
