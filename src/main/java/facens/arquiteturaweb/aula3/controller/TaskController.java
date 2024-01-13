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
    /*
        A anotação @Valid é aplicada ao parâmetro @RequestBody Task task. Isso instrui o Spring a realizar a validação
        automática da entidade Task antes de executar o método.

        Quando você usa a anotação @Valid diretamente antes do objeto validado, o BindingResult deve seguir imediatamente
        o objeto validado na assinatura do método. Isso permite ao Spring armazenar os resultados da validação no BindingResult.
        Se houver erros de validação, o Spring preencherá o BindingResult com esses erros.
     */
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task, BindingResult result) {

        /*
            O método verifica se há erros de validação usando result.hasErrors(). Se houver, ele percorre os erros usando
            result.getFieldErrors() e constrói um mapa de erros.

            Se houver erros, o controlador retorna uma resposta HTTP 400 Bad Request com o mapa de erros no corpo da resposta.
            Caso contrário, retorna uma resposta HTTP 201 Created com a tarefa criada.
         */
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
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
