package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vacationproject.lobster.domain.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
