package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.exceptions.TarefaNaoEncontradaException;
import facens.arquiteturaweb.aula3.modelo.Task;
import facens.arquiteturaweb.aula3.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Controller ´Privado
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

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
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @DeleteMapping("/remove/{id}")
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
