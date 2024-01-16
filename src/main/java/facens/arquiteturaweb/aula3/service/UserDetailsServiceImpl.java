package facens.arquiteturaweb.aula3.service;
import facens.arquiteturaweb.aula3.modelo.User;
import facens.arquiteturaweb.aula3.modelo.UserRepository;
import facens.arquiteturaweb.aula3.seguranca.UserAuthenticated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return //userRepository.findByUsername(username)
                Optional.of(new User("user", "$2a$12$gS7HUFEb/UBSxl.v6Q/peeHQo0dWQ3TADOw0aQQ6puyN3ZZ.LB2VO"))
                .map(UserAuthenticated::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

}
