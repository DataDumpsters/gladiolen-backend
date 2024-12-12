package Project40.gladiolen_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.NonNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;
    @NotBlank
    private String registryNumber;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be a combination of letters and numbers")
    private String password;

    @Column(name = "email_verified", nullable = false)
    private boolean isEmailVerified;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void setUp() {
        this.createdAt = LocalDateTime.now(ZoneId.of("+00:00"));
    }

    @ManyToOne
    @JoinColumn(name = "union_id", referencedColumnName = "id")
    private Union union;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tshirt_id", referencedColumnName = "id")
    private Tshirt tshirt;

    @ManyToMany
    private List<Shift> shifts;

}
