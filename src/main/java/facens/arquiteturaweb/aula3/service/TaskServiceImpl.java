package facens.arquiteturaweb.aula3.service;

import facens.arquiteturaweb.aula3.exceptions.TarefaNaoEncontradaException;
import facens.arquiteturaweb.aula3.modelo.Task;
import facens.arquiteturaweb.aula3.modelo.TaskRepository;
import facens.arquiteturaweb.aula3.modelo.external.Book;
import facens.arquiteturaweb.aula3.service.external.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final BookService bookService;
    /*
    A anotação @PersistenceContext é uma anotação do Java Persistence API (JPA) que é usada para injetar uma instância
    do EntityManager em uma classe gerenciada pelo container, como em um componente Spring.

    O EntityManager é uma interface no JPA que fornece métodos para interagir com o contexto de persistência JPA. Ele é
    usado para realizar operações de leitura e gravação no banco de dados, como persistir entidades, recuperar entidades
    por meio de consultas, atualizar entidades e excluir entidades.
     */
    @PersistenceContext
    private EntityManager entityManager;


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

    @Override
    public void convertBooksToTasks() {
        List<Book> books = bookService.getAllBooks();

        for (Book book : books) {
            Task task = new Task();

            // Atribua um identificador específico para a Task com base no livro
            task.setId(generateTaskId());

            task.setTitle(book.getTitle());
            task.setDescription("Descrição da tarefa com base no livro: " + book.getTitle());

            taskRepository.save(task);
        }
    }

    // Método criado apenas para gerar um novo id baseado no maior id, já que a tabela não tem autoincremento
    private Long generateTaskId() {
        // Consulta para obter o maior ID existente na tabela de Task
        Query query = entityManager.createQuery("SELECT MAX(t.id) FROM Task t");
        Long maxId = (Long) query.getSingleResult();

        // Se não há registros, maxId será null, então definimos para 0
        if (maxId == null) {
            maxId = 0L;
        }

        // Adiciona 1 para obter o próximo ID
        return maxId + 1;
    }
}
