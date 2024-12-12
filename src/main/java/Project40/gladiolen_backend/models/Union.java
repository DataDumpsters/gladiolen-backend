package Project40.gladiolen_backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "unions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Union  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String postalCode;
    private String municipality;
    private String vatNumber;
    private String accountNumber;
    private int numberOfParkingTickets;

    @OneToMany(mappedBy = "union")
    private List<User> users;

//    public int getTotalHours() {
//        return users.stream().mapToInt(User::getHoursWorked).sum();
//    }
}
