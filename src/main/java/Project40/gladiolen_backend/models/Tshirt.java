package Project40.gladiolen_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tshirts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Tshirt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Job job;
    private int quantity;

    @OneToOne(mappedBy = "tshirt")
    private User user;
}
