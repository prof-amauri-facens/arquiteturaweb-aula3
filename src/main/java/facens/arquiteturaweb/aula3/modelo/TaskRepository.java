package facens.arquiteturaweb.aula3.modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /*
    Query Methods utilizando as converções de nomes para gerar consultas automaticamente
     */
    List<Task> findByCategoriaId(Long categoriaId);
    List<Task> findByCategoriaNome(String nomeCategoria);
    List<Task> findByCategoriaNomeContaining(String nomeParcialCategoria);

    /*
    Quer Methods com consultas custmomizadas
     */

    // Recuperar o total de tarefas por ID da categoria usando JPQL
    @Query("SELECT COUNT(t) FROM Task t WHERE t.categoria.id = :categoriaId")
    Long countTasksPorCategoriaId(@Param("categoriaId") Long categoriaId);

    // Recuperar o total de tarefas por nome parcial da categoria usando JPQL
    @Query("SELECT COUNT(t) FROM Task t WHERE t.categoria.nome LIKE %:nomeParcial%")
    Long countTasksPorNomeCategoriaParcial(@Param("nomeParcial") String nomeParcial);
}


