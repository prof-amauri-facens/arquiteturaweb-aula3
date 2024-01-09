package facens.arquiteturaweb.aula3.modelo;

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
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();
        return task;
    }
}

