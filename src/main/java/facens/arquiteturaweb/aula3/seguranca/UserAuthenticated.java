package facens.arquiteturaweb.aula3.seguranca;

import java.util.Collection;
import java.util.List;

import facens.arquiteturaweb.aula3.modelo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 Vai representar o usuário logado. Ele precisa implementar a interface UserDetails para que o Spring Security possa
 gerenciar a autenticação sozinho.
 */
public class UserAuthenticated implements UserDetails {

    /*
    O UserAuthenticated vai precisar dos dados do usuário que serão recuperados no banco, por isso criamos a injeção
    para esse objeto aqui
     */
    private final User user;

    public UserAuthenticated(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /*
    Para fazermos a parte de autorização precisamos das autorithies. Elas podem ser recuperadas da forma como a aplicação
    precisar e injetadas aqui. Nós estamos usando uma fixa e não recuperada pois o foco aqui é a autenticação e não a
    autorização.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    /*
    Daqui para baixo são todos métodos para caracterizar o estado de um usuário. Se ele não está expirado, bloqueado,
    expirado e habilitado. Estamos retornando tudo como true, porém poderiamos ter essa informação sendo checada com
    algum tipo de logica e obtendo isso do banco de dados por exemplo.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
