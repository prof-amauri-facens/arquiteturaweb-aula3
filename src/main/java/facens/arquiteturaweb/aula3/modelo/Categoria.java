package facens.arquiteturaweb.aula3.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    /*
    A anotação @GeneratedValue em Java é usada em conjunto com a anotação @Id para especificar como os valores dos
    identificadores primários das entidades devem ser gerados automaticamente pelo provedor JPA (Java Persistence API).
    A @GeneratedValue define a estratégia de geração de valores para uma coluna de identificador primário no banco de dados.

    GenerationType.IDENTITY é uma estratégia de geração de chave primária que delega a responsabilidade de geração do valor
    do identificador para o banco de dados. Ao usar GenerationType.IDENTITY, o valor do identificador é gerado pelo próprio
    banco de dados no momento da inserção do registro na tabela. O JPA insere a entidade sem fornecer explicitamente um
    valor para o identificador, permitindo que o banco de dados atribua automaticamente um valor único para a coluna de
    chave primária.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}

