package Project40.gladiolen_backend.repositories;

import Project40.gladiolen_backend.models.Union;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnionRepository extends JpaRepository<Union, Long> {
}
