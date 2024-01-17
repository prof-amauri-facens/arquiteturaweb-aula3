package facens.arquiteturaweb.aula3.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    /*
    Utilizamos uma dependencia do oauth2 para ter acesso a esse encoder, que fara toda a parte da codifiação e descodificação
    dos tokens JWT sem que isso precise ser implementado manualmente.
     */
    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }


    /*
    Para gerar o token é necessário um objeto Authentication do Spring Security, que irá conter todas as informações
    referentes a autenticação do usuário
     */
    public String generateToken(Authentication authentication) {
        /*
        Tokens tem inicio e fim de validade. Nesse caso, ele começa agora e termina em aproximadamente uma hora (notação em segundos)
         */
        Instant now = Instant.now();
        long expiry = 3600L;

        /*
        Para o token precisamos dos escopos, que são obtidos a partir das Authorities dos usuários. Seiram as permissões para
        um determinado usuario, em um sistema de autorização.
         */
        String scope = authentication
                .getAuthorities().stream()
                /*
                Usamos o map para obter apenas as strings das Granted Authorities e depois no collect transforma tudo em
                uma unica string, com cada Authority separado por um espaço.

                A notação GrantedAuthority::getAuthority chama o método getAuthority para cada GrantedAuthority
                 */
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors
                        .joining(" "));



        /*
        Claims são propriedades do token, que iremos utilizar para gerar o token codificado final
         */
        JwtClaimsSet claims = JwtClaimsSet.builder()
                // Qual sistema esta emitindo.
                .issuer("facens.arquiteturaweb.sprigsecurity")
                // Quando ele começa a valer
                .issuedAt(now)
                // Quando deixa de valer (inicio e fim são efetivamente datas com horarios (em milisegundos no final) por isso precisa ser somado de um ao outro
                .expiresAt(now.plusSeconds(expiry))
                // Dono do token, que é o username
                .subject(authentication.getName())
                // Claim customizada com os escopos
                .claim("scope", scope)
                .build();

        // Codificamos o token com as informações e retornamos o token
        return encoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

}