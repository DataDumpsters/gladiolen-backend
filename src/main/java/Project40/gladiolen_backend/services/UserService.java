package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.User;
import Project40.gladiolen_backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Add some users
        if (userRepository.count() <= 0) {
            User user1 = new User();
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEmail("johndoe@test.com");
            user1.setPassword("password");
            userRepository.save(user1);
        }
    }
}
