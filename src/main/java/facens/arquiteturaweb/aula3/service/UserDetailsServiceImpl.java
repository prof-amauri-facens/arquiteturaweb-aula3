package facens.arquiteturaweb.aula3.service;
import facens.arquiteturaweb.aula3.modelo.User;
import facens.arquiteturaweb.aula3.modelo.UserRepository;
import facens.arquiteturaweb.aula3.seguranca.UserAuthenticated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
Classe que manipula elementos UserDetails. No nosso caso, a implementação de UserDetails é a UserAuthenticate.
UserDetailsService também é uma interface, assim como UserDetails, e precisa ter seu método implementado para que o
Spring Security consiga obter o usuario para autenticação.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    Esse método será chamado automaticamente quando for solicitado que um usuário seja autenticado. Nós sabemos como
    buscar um usuário, pois temos um repositorio para isso, então o injetamos aqui para recuperar o usuário, caso ele
    exista.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*
        Quando estava montando esse exemplo, banco de dados não estava reconhendo corretamente a tabela de user
        devido alguns testes que eu havia feito previamente. Por isso coloquei um usuario hardcoded para exemplo.
        A senha aqui é "senha" (sem as aspas) e o usuário e user. Em sua máquina deverá funcionar adequadamente
         */
        return //userRepository.findByUsername(username)
                Optional.of(new User("user", "$2a$12$gS7HUFEb/UBSxl.v6Q/peeHQo0dWQ3TADOw0aQQ6puyN3ZZ.LB2VO"))
                 /*
                 O Spring security não sabe lidar com a entidade usuario, mas sim com um UserDetails. Por isso aqui
                 mapeamos o usuario recuperado criando um novo UserAuthenticated

                 Quanto a notação:
                 UserAuthenticated::new: Isso é conhecido como uma referência de construtor. Ele indica que você está
                 referenciando o construtor da classe UserAuthenticated. Em outras palavras, você está usando o construtor
                 da classe UserAuthenticated para criar uma nova instância.

                 UserAuthenticated::new dentro do map: Isso significa que a função de mapeamento (o que está dentro do map)
                 irá criar uma nova instância de UserAuthenticated usando o construtor dessa classe. Como estamos chamando
                 no Optional que contem o usuario, e UserAuthenticated possui um construtor que recebe um usuário, ele é passado
                 para o construtor, criando o novo objeto. Lembrando que isso ocorre apenas se o Optional conter um uaurio,
                 caso ele não seja encontrado e o Optional "vazio" ele cai no lambda orElseThrow, lançando uma exeção
                 de usuario não encontrado
                  */
                .map(UserAuthenticated::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

}
