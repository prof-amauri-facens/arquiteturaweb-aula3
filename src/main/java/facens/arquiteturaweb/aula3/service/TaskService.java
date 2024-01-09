package facens.arquiteturaweb.aula3.service;

import facens.arquiteturaweb.aula3.modelo.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task);

    void removeTask(Long id);
}

