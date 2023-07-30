package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.Member;
import vacationproject.lobster.domain.User;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByGroupIdAndUserId(Group groupId, User userId);
}
