package facens.arquiteturaweb.aula3.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;

    /*
    A anotação @ManyToOne é usada para indicar que a associação entre duas entidades é do tipo muitos-para-um. Isso
    significa que vários registros de uma entidade podem estar associados a um único registro de outra entidade.
     */
    @ManyToOne
    /*
    A anotação @JoinColumn é usada para especificar a coluna na tabela do banco de dados que está sendo usada para mapear
    o relacionamento entre as entidades.

    name = "categoria_id": Indica o nome da coluna na tabela do banco de dados que mantém a chave estrangeira para a
    entidade relacionada (Categoria, neste caso). Isso significa que a tabela de Task terá uma coluna chamada categoria_id
    que será usada para estabelecer a associação com a tabela de Categoria.
     */
    @JoinColumn(name = "categoria_id")
    /*
    Nós não temos apenas o ID da categoria como na tabela, nós temos o objeto completo.
     */
    private Categoria categoria;
}


