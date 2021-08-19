package restaurante.gif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import restaurante.gif.config.token.Token;
import restaurante.gif.config.form.LoginForm;
import restaurante.gif.service.TokenService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Token> autenticar(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getSenha());
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.gerarToken(authentication);
            return new ResponseEntity(new Token(token, "Bearer"), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
