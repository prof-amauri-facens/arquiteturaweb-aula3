package facens.arquiteturaweb.aula3.modelo;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import facens.arquiteturaweb.aula3.config.HibernateConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    /*
     É uma instância final da SessionFactory do Hibernate, que será utilizada
    para criar e gerenciar sessões com o banco de dados.
     */
    private final SessionFactory sessionFactory;

    public TaskRepositoryImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Task> findAll() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        /*
        Cria uma query HQL para buscar todas as instâncias da entidade Task no banco de dados e retorna uma lista de tarefas.
         */
        List<Task> tasks = session.createQuery("FROM Task", Task.class).getResultList();
        // Confirma a transação, aplicando as operações realizadas na sessão.
        transaction.commit();
        return tasks;
    }

    @Override
    public Task findById(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Task task = session.find(Task.class, id);
        transaction.commit();
        return task;
    }

    @Override
    public Task save(Task task) {
//        Session session = getSession();
//        Transaction transaction = session.beginTransaction();
//        session.persist(task);
//        transaction.commit();
//        return task;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(task);
        transaction.commit();
        return task;
    }

    @Override
    public Task delete(int id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        // Consulta HQL para deletar a Task pelo ID
        String hqlQuery = "DELETE FROM Task WHERE id = :taskId";
        Query query = session.createQuery(hqlQuery);
        query.setParameter("taskId", id);

        int deletedCount = query.executeUpdate(); // Executa a operação de exclusão e retorna a quantidade de registros deletados
        transaction.commit();

        if (deletedCount > 0) {
            // Se algum registro foi deletado, você pode retornar uma instância vazia de Task ou uma mensagem de confirmação
            return new Task(); // ou null, dependendo do comportamento desejado
        } else {
            // Se nenhum registro foi deletado, pode-se lançar uma exceção ou retornar null, dependendo do tratamento esperado
            return null;
        }
    }
}

