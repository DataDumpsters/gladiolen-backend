package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.User;
import Project40.gladiolen_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collections;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserBuilder builder = withUsername(user.getEmail());
        builder.password(user.getPassword());
        builder.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return builder.build();
    }
}