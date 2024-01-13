package facens.arquiteturaweb.aula3.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título não pode estar em branco")
    @Size(min = 3, max = 50, message = "O título deve ter entre 3 e 50 caracteres")
    private String title;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    private String description;

    @NotNull(message = "A categoria não pode ser nula")
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
