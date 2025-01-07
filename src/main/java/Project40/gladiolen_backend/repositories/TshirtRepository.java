package Project40.gladiolen_backend.repositories;

import Project40.gladiolen_backend.models.Tshirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TshirtRepository extends JpaRepository<Tshirt, Long> {

    @Query("SELECT t.job AS job, t.sex AS sex, t.size AS size, SUM(t.quantity) AS totalQuantity " +
            "FROM Tshirt t GROUP BY t.job, t.sex, t.size ORDER BY t.job, t.sex, t.size")
    List<Object[]> findTshirtCountsByRoleSexAndSize();
}
