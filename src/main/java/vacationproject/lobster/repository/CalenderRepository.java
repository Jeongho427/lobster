package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vacationproject.lobster.domain.Calender;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
}
