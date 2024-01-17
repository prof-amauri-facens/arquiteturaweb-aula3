package facens.arquiteturaweb.aula3.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    // Injetamos o JwtService para a geração do token.
    private JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Ao autenticar, retornamos o token gerado a partir das iformações do usuario sendo autenticado.
    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}