package facens.arquiteturaweb.aula3.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConfig {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*
    buildSessionFactory(): Este método é responsável por criar a SessionFactory. Ele utiliza a classe
    StandardServiceRegistryBuilder para criar um registro de serviços com base no arquivo de configuração hibernate.cfg.xml.
    O arquivo hibernate.cfg.xml contém as configurações do Hibernate, como detalhes de conexão do banco de dados, dialecto
    SQL, entre outros. Depois de configurar o registro de serviços, utiliza MetadataSources para construir os metadados
    (informações sobre o mapeamento de classes Java para tabelas do banco de dados) e, em seguida, cria a SessionFactory.
     */
    private static SessionFactory buildSessionFactory() {
        /*
        O StandardServiceRegistry é uma central de configurações do Hibernate, usada para configurar e registrar serviços.
        StandardServiceRegistryBuilder() é uma classe para construir uma instância do StandardServiceRegistry.

        .configure("hibernate.cfg.xml"): O método configure() é usado para especificar o arquivo de configuração do Hibernate,
        onde estão contidas as configurações do banco de dados e do Hibernate. O argumento "hibernate.cfg.xml" indica
        o caminho para o arquivo XML que contém as configurações.

        .build(): Este método build() constrói o StandardServiceRegistry com base nas configurações fornecidas.

        return new MetadataSources(registry).buildMetadata().buildSessionFactory(): MetadataSources é usado para construir
        os metadados das entidades, que descrevem as classes Java que estão mapeadas para tabelas no banco de dados.
        O método buildMetadata() constrói esses metadados com base no registro criado.

        buildSessionFactory() finaliza a configuração, gerando a SessionFactory que será usada para criar e gerenciar as sessões do Hibernate.

         */
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // Caminho para o arquivo de configuração do Hibernate
                    .build();
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Falha na criação do SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /*
    getSessionFactory(): Este método estático é usado para obter a instância única de SessionFactory criada pela aplicação.
    É comum criar um método estático para fornecer acesso à SessionFactory em várias partes do código onde é necessário
    criar sessões para interagir com o banco de dados usando o Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
