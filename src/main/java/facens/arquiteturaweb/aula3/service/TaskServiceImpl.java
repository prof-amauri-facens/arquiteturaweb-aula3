package facens.arquiteturaweb.aula3.service;

import facens.arquiteturaweb.aula3.exceptions.TarefaNaoEncontradaException;
import facens.arquiteturaweb.aula3.modelo.Task;
import facens.arquiteturaweb.aula3.modelo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    /*
    Não é necessário graças a notação
     */
//    public TaskServiceImpl(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void removeTask(Long id) {
        // Recuperar para verificar se o dado realmente existe
       Task task = getTaskById(id);
       // Se não existe, lançamos uma exceção para que seja capturada no controller de forma que o erro seja exibido na resposta
       if (task == null)
           throw new TarefaNaoEncontradaException("A tarefa com ID: " + id + " não foi encontrada.");

       taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByCategoriaId(Long categoriaId) {
        return taskRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Task> getTasksByCategoriaNome(String nomeCategoria) {
        return taskRepository.findByCategoriaNome(nomeCategoria);
    }

    @Override
    public List<Task> getTasksByCategoriaNomeContaining(String nomeParcialCategoria) {
        return taskRepository.findByCategoriaNomeContaining(nomeParcialCategoria);
    }

    @Override
    public Long countTasksByCategoriaId(Long categoriaId) {
        return taskRepository.countTasksPorCategoriaId(categoriaId);
    }

    @Override
    public Long countTasksByPartialCategoriaName(String nomeParcial) {
        return taskRepository.countTasksPorNomeCategoriaParcial(nomeParcial);
    }
}
