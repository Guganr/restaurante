package restaurante.gif.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import restaurante.gif.repository.UsuarioRepository;
import restaurante.gif.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {


    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void  configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //endpoints permitidos sem autenticação
                .antMatchers(HttpMethod.POST, "/auth/").permitAll()
                .antMatchers(HttpMethod.POST, "/usuario/").permitAll()
                //Qualquer requisição necessita de autenticação
                .anyRequest().authenticated()
                //desativa a verificação do csrf
                .and().csrf().disable()
                //define que a autenticação deve ser feita de forma stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //chama a classe AutenticacaoViaTokenFilter antes da padrão para fazer o tratamento do token
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
