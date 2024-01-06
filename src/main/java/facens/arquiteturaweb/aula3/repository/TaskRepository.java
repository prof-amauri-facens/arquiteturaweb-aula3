package facens.arquiteturaweb.aula3.repository;

import facens.arquiteturaweb.aula3.modelo.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById(Long id);
    Task save(Task task);
}

