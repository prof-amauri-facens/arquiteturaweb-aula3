package facens.arquiteturaweb.aula3.modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Para usar a convenção, não é necessário sequer colocar a lista de métodos aqui
}


