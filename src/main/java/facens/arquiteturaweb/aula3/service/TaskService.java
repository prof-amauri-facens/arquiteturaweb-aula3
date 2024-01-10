package facens.arquiteturaweb.aula3.service;

import facens.arquiteturaweb.aula3.modelo.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    void removeTask(Long id);

    List<Task> getTasksByCategoriaId(Long categoriaId);
    List<Task> getTasksByCategoriaNome(String nomeCategoria);
    List<Task> getTasksByCategoriaNomeContaining(String nomeParcialCategoria);

    Long countTasksByCategoriaId(Long categoriaId);

    Long countTasksByPartialCategoriaName(String partialName);
}
