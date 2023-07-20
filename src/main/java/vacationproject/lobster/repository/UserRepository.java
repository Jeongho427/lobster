package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vacationproject.lobster.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
