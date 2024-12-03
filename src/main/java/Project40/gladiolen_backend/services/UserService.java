package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.models.User;
import Project40.gladiolen_backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TshirtService tshirtService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Add some users
        if (userRepository.count() <= 1) {
            User user1 = new User();
            user1.setFirstName("Joan");
            user1.setLastName("Doe");
            user1.setEmail("joandoe@test.com");
            user1.setPassword(passwordEncoder.encode("password"));
            userRepository.save(user1);
        }
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Er bestaat reeds een gebruiker met email: " + user.getEmail());
        }
        if (user.getTshirt() != null) {
            tshirtService.createTshirt(user.getTshirt());
        }
        User user1 = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .registryNumber(user.getRegistryNumber())
                .password(passwordEncoder.encode(user.getPassword()))
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

    @Transactional
    public void updateUser(Long id, User user) {
        User user1 = userRepository.findById(id).orElse(null);
        if (user1 != null) {
            if (user1.getTshirt() != null) {
                tshirtService.updateTshirt(user1.getTshirt().getId(), user.getTshirt());
            }
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
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            tshirtService.deleteTshirt(user.getTshirt().getId());
        }
        userRepository.deleteById(id);
    }
}