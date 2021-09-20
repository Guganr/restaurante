package restaurante.gif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableCaching
public class RestauranteApplication {

    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        SpringApplication.run(RestauranteApplication.class);
    }
}
