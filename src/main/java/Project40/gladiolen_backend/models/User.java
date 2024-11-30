package Project40.gladiolen_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String phoneNumber;
    @NonNull
    private String email;
    @NonNull
    private Role role;
    @NonNull
    private String registryNumber;
    @NonNull
    private String password;

    @ManyToOne
    private Union union;

    @OneToOne(cascade = CascadeType.ALL)
    private Tshirt tshirt;

    @ManyToMany
    private List<Shift> shifts;

}
