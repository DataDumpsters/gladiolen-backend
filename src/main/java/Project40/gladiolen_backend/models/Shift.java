package Project40.gladiolen_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;

@Entity
@Table(name = "shifts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp shiftStart;
    private Timestamp shiftEnd;

}
