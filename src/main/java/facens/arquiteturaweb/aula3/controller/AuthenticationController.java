package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller Aberto
@RestController
public class AuthenticationController {

    // Injeção do serviço de autenticação, que irá retornar um token quando a autenticação ocorrer com sucesso.
    @Autowired
    private AuthenticationService authenticationService;


    // Rota de autenticação
    @PostMapping("/authenticate")
    // Aqui é passado as informaçãoes de autenticação e montado o objeto Authentication com o username e password providos na requisição
    // Isso é feito de forma automatica pelo Spring Security, a construção desse objeto
    public String authenticate(
            Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
}
