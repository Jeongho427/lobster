package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vacationproject.lobster.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
