package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.exceptions.TarefaNaoEncontradaException;
import facens.arquiteturaweb.aula3.modelo.Task;
import facens.arquiteturaweb.aula3.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /*
    a classe ResponseEntity permite que você defina não apenas o corpo da resposta, mas também o status HTTP, os
    cabeçalhos e outras informações relevantes para enviar como resposta a uma requisição HTTP.
     */
    public ResponseEntity<String> removeTask(@PathVariable Long id) {
        try {
            taskService.removeTask(id);
            return ResponseEntity.ok("Item deletado com sucesso!");
        } catch (TarefaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/byCategoryId/{id}")
    public List<Task> getTasksByCategoryId(@PathVariable Long id) {
        return taskService.getTasksByCategoriaId(id);
    }

    @GetMapping("/byCategoryName/{name}")
    public List<Task> getTasksByCategoryName(@PathVariable String name) {
        return taskService.getTasksByCategoriaNome(name);
    }

    @GetMapping("/byCategoryNameContaining/{partialName}")
    public List<Task> getTasksByCategoryNameContaining(@PathVariable String partialName) {
        return taskService.getTasksByCategoriaNomeContaining(partialName);
    }

    @GetMapping("/countByCategoriaId/{categoriaId}")
    public Long countTasksByCategoriaId(@PathVariable Long categoriaId) {
        return taskService.countTasksByCategoriaId(categoriaId);
    }

    @GetMapping("/countByPartialCategoriaName/{partialName}")
    public Long countTasksByPartialCategoriaName(@PathVariable String partialName) {
        return taskService.countTasksByPartialCategoriaName(partialName);
    }
}
