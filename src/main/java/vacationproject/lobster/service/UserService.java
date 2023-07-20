package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddUserRequest;
import vacationproject.lobster.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //User 등록
    public User save(AddUserRequest userRequest) {
        return userRepository.save(userRequest.toEntity());
    }

    //User 전체 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //User id로 조회
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //User 삭제
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    //User 수정
   /* @Transactional
    public User update(Long id, UpdatableUserRequest request) {
        User user = UserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: ") + id);

        //article.update(request.getTitle(), request.getContent());
        return user;
    }*/

}