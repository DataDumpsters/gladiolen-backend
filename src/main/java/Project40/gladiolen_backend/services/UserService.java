package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.User;
import Project40.gladiolen_backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createUser(User user){
        User user1 = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .registryNumber(user.getRegistryNumber())
                .password(user.getPassword())
                .union(user.getUnion())
                .tshirt(user.getTshirt())
                .shifts(user.getShifts())
                .build();
        userRepository.save(user1);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, User user) {
        User user1 = userRepository.findById(id).orElse(null);
        if (user1 != null) {
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setEmail(user.getEmail());
            user1.setRole(user.getRole());
            user1.setRegistryNumber(user.getRegistryNumber());
            user1.setPassword(user.getPassword());
            user1.setUnion(user.getUnion());
            user1.setTshirt(user.getTshirt());
            user1.setShifts(user.getShifts());
            userRepository.save(user1);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
