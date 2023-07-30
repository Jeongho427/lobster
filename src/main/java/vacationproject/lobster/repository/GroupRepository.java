package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vacationproject.lobster.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Modifying
    @Query("UPDATE Group g SET g.memberCnt = g.memberCnt + 1 WHERE g.id = :id")
    void incrementMemberCount(@Param("id") Long id);
}
