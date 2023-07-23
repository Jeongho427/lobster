package vacationproject.lobster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vacationproject.lobster.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserId(@Param("user_id")String userId);
    User findByPhoneNum(@Param("phone_num") String phoneNum);
}
