package facens.arquiteturaweb.aula3.modelo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para busca do usuário
public interface UserRepository extends JpaRepository<User, String> {

    /*
    A classe Optional é projetada para lidar com a possibilidade de valores nulos de maneira mais segura e expressiva, evitando
    exceções do tipo NullPointerException

    Nesse exemplo ele retorna sempre um Objeto não nulo Optional, que pode ou não conter um usuário dentro dele.
     */
    Optional<User> findByUsername(String username);
}
