package Project40.gladiolen_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.NonNull;
import jakarta.validation.constraints.NotBlank;

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
    @NotBlank
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String lastName;
    private String phoneNumber;
    @Column(unique = true)
    @Email
    private String email;
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private Role role;
    @NotBlank
    private String registryNumber;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be a combination of letters and numbers")
    private String password;

    @ManyToOne
    private Union union;

    @OneToOne(cascade = CascadeType.ALL)
    private Tshirt tshirt;

    @ManyToMany
    private List<Shift> shifts;

}
