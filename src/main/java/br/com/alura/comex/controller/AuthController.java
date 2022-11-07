package br.com.alura.comex.controller;

import br.com.alura.comex.entity.Usuario;
import br.com.alura.comex.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public AuthController() {
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid UsuariosController.RequestDto form) {
        System.out.println(form);
        try {
            UsernamePasswordAuthenticationToken login = form.toToken();
            Authentication auth = authenticationManager.authenticate(login);
            String token = tokenService.getToken(auth);
            return ResponseEntity.ok(new Dto("Bearer", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    public record Dto(String tipo, String token) {
    }

}
