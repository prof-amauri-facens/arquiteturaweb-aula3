package facens.arquiteturaweb.aula3.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
// Notação para habilitar a segurança na aplicação
@EnableWebSecurity
/*
Orquestrador de todas as necessidades do Spring Security e do Jwt para a autenticação
 */
public class SecurityConfig {

    /*
    Aqui ocorre a injeção das chaves publicas e privadas. Usando a notação @Value podemos ler valores do aaplication.properties
     */
    @Value("${jwt.public.key}")
    private RSAPublicKey key;
    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    /*
    Essa anotação em um método em uma classe Spring indica ao contêiner IoC (Inversion of Control) que o método produz um
    bean gerenciado por Spring. Ou seja, um objeto que a criação e ciclo de vida será gerenciado pelo Spring.
     */
    @Bean
    // Metodo para configurar a segurança em cada rota da aplicação
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
         Tokens CSRF fazem uso de cookies para prover a autenticação. Como não será utilizado, já que serão usados tokens
         JWT, o ideal é desabilitar para evitar vulnerabilidades
         */
        http.csrf(AbstractHttpConfigurer::disable)
                // Autorizar requisições http
                .authorizeHttpRequests(
                        auth -> auth
                                // Casa o padrão (no caso a string, que é o nosso endpoint) e em seguida autoriza que tudo possa acessar esse endpoint
                                .requestMatchers("authenticate").permitAll()
                                // E para todas as outras requisições, requer que o usuario esteja autenticado, ou seja, possua um token valido
                                .anyRequest().authenticated())
                // Aqui diz como vai ser a autenticação na requisição. Aqui vai dizer como vamos enviar os dados. E dependendo de como os dados
                // são enviados o objeto Authentication no controlador é construido
                .httpBasic(Customizer.withDefaults())
                // É aqui que adiconarmos os recursos de codificação e decodificação do Jwt
                .oauth2ResourceServer(
                        conf -> conf.jwt(
                                jwt -> jwt.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    /*
    Aqui é o codificador da senha do usuario
     */
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    Para a decodificação e codificação vamos precisar de uma chave publica e privada. O caminho apara elas são condfigurados
    no application.properties.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        // A decodificação ocorre com a chave publica
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        /*
        Json Web key construida com a RSAKey, usando a chave publica e a privada
         */
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        // Aqui criamos as chaves no formato jwk
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        // E chamamos o encoder com o conjunto de chaves
        return new NimbusJwtEncoder(jwks);
    }
}
