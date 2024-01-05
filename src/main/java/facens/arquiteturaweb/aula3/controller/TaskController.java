package facens.arquiteturaweb.aula3.controller;

import facens.arquiteturaweb.aula3.modelo.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
  A anotação @SpringBootApplication é uma combinação de três anotações diferentes fornecidas pelo Spring Framework:
  @Configuration, @EnableAutoConfiguration, e @ComponentScan. Ela é comumente usada na classe principal de uma aplicação
  Spring Boot para simplificar a configuração e inicialização da aplicação.

  Explicação mais detalhada sobre cada uma das anotações que compõem @SpringBootApplication:

  @Configuration: Indica que a classe é uma fonte de definições de beans para o contexto do Spring. Classes anotadas com
  @Configuration são usadas para definir beans usando o método @Bean. Esta anotação é usada em classes que definem
  configurações para o contexto do Spring.

  @EnableAutoConfiguration: Habilita a configuração automática do Spring Boot. O Spring Boot tenta adivinhar automaticamente
  a configuração da aplicação com base nas dependências do classpath e em outras configurações. Isso significa que muitas
  configurações são feitas automaticamente sem a necessidade de configuração manual.

  @ComponentScan: Essa anotação diz ao Spring para procurar por componentes (como @Component, @Service, @Repository, @Controller, etc.)
  no pacote da classe anotada e em subpacotes. O Spring registra esses componentes e os torna disponíveis para injeção de
  dependência e outras funcionalidades.
*/
@SpringBootApplication

/*
Combina as anotações @Controller e @ResponseBody (Quando um método de um controlador Spring é anotado com @ResponseBody,
o valor retornado por esse método é convertido automaticamente para o formato de saída desejado (como JSON ou XML) e
inserido diretamente no corpo da resposta HTTP.). Mapeia os métodos para endpoints REST e converte automaticamente as
respostas para JSON ou XML.
 */
@RestController

/*
Especifica o prefixo de URL para todos os endpoints do controlador. Neste caso, todos os endpoints estão sob o caminho /api/tasks.
 */
@RequestMapping("/api/tasks")
public class TaskController {

    // Simulação de dados com ArrayLists
    private List<Task> tasks = new ArrayList<>(); // Lista de tarefas
    private Long nextId = 1L; // Contador para gerar IDs únicos

    // Construtor para adicionar exemplos pré-fixados de tarefas
    public TaskController() {
        // Adiciona alguns exemplos de tarefas ao iniciar a aplicação
        tasks.add(new Task(nextId++, "Estudar para a prova de matemática", "Revisar álgebra e geometria"));
        tasks.add(new Task(nextId++, "Fazer compras no mercado", "Comprar itens de casa"));
        tasks.add(new Task(nextId++, "Preparar apresentação para o trabalho", "Preparar slides e dados para apresentação"));
    }

    /*
     Especifica um endpoint para retornar a lista de tarefas e outro para retornar uma tarefa específica com base no índice.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    /*Nesse caso, existe a configuração da anotação com uma variavel de URL para selecionar qual task será recuperada*/
    @GetMapping("/{task}")
	/*
	@PathVariable
	Papel: Associa o valor do parâmetro do método ao valor de uma variável na URL.
	Detalhes: Neste caso, permite acessar o índice da tarefa desejada a partir da URL.
	 */
    public Task getTaskByIndex(@PathVariable int id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /*
    Papel: Mapeia métodos para manipulação de solicitações POST.
    Detalhes: Define um endpoint para adicionar uma nova tarefa à lista. O corpo da requisição é convertido automaticamente
    para o tipo especificado no parâmetro do método (@RequestBody).
     */
    @PostMapping("/add")
	/*
	@RequestBody
	Papel: Indica que o argumento do método deve ser extraído do corpo da requisição.
	Detalhes: Permite a passagem de dados no corpo de uma solicitação POST para o método do controlador.
	 */
    public Task addTask(@RequestBody Task task) {
        tasks.add(task);
        System.out.println("Tarefa adicionada: " + task); // Exibe os dados recebidos via POST no console
        return task; // Retorna a tarefa adicionada
    }

}

