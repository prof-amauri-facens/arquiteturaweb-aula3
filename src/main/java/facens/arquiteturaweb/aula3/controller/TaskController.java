package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.modelo.Task;
import facens.arquiteturaweb.aula3.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    /*
     a injeção de dependência é visível no construtor da classe TaskController. TaskController depende de TaskService,
     que é passado como um parâmetro no construtor. O Spring será responsável por injetar uma instância de TaskService
     quando criar uma instância de TaskController.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/add")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Na configuração do endepoint é necessário adicionar entre chaves aonde ficará na url a variavel configurada abaixo
    @DeleteMapping("/remove/{id}")
    // A notação PathVariable permite que a API receba informações que estão contidas na URL
    public Task removeTask(@PathVariable int id) {
        return taskService.removeTask(id);
    }
}
