package facens.arquiteturaweb.aula3.modelo;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    Task findById(Long id);

    Task save(Task task);
}

