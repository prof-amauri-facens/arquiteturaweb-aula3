package facens.arquiteturaweb.aula3.service.external;

import facens.arquiteturaweb.aula3.modelo.external.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private RestTemplate restTemplate; // Você pode usar RestTemplate para fazer chamadas HTTP para a outra aplicação

    @Override
    public List<Book> getAllBooks() {
        // Supondo que a outra aplicação tenha um endpoint /api/books que retorna a lista de livros em formato JSON
        String apiUrl = "http://localhost:8080/api/books";

        // Faz uma chamada HTTP GET para obter a lista de livros
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(apiUrl, Book[].class);

        /*
        PARA EXERCICIO

        Para o exercicio vocês irão precisar recuperar um valor booleano. Aqui segue um exemplo de como
        // Faça a requisição e receba a resposta como ResponseEntity<Boolean>
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(url, Boolean.class);

        // Extraia o valor booleano da resposta
        Boolean valorBooleano = responseEntity.getBody();
         */

        // Converte a array de livros para uma lista

        /*
        responseEntity.getBody(): O método getBody() retorna o corpo da resposta, que pode ser nulo se a resposta não contiver
        um corpo ou se houver um erro durante a solicitação.

        Objects.requireNonNull(...): Objects.requireNonNull() é um método estático da classe java.util.Objects introduzido
        no Java 7. Ele é utilizado para verificar se o argumento passado para ele é não nulo. Se o argumento for nulo,
        uma exceção NullPointerException será lançada.

        Arrays.asList(...): Arrays.asList() é um método estático da classe java.util.Arrays que converte um array para
        uma lista. Ele aceita uma variedade de argumentos ou um array como argumento. No caso específico, está sendo usado
        para converter um array de objetos em uma lista.

         */
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}

