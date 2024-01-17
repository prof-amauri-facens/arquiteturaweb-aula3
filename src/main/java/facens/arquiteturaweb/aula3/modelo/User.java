package facens.arquiteturaweb.aula3.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Representação de um usuário do sistema
@Entity
@Table(name = "user", schema="public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String username;
    private String password;

}
