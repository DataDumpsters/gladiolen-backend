package Project40.gladiolen_backend.models;

import jakarta.persistence.*;
import lombok.*;

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
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Role role;
    private String registryNumber;
    private String password;
    private int hoursWorked;

    @ManyToOne
    private Union union;

    @OneToOne(cascade = CascadeType.ALL)
    private Tshirt tshirt;

    @ManyToMany
    private List<Shift> shifts;

}
