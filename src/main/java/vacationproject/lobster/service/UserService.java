package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.AddUserRequest;
import vacationproject.lobster.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 가입
    public void registerMembership(String name, String userId, String email, String password, String phoneNum) {

        User user = new User();
        user.setUserId(userId); // 변수 이름 변경
        user.setUserName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNum(phoneNum); // 변수 이름 수정

        userRepository.save(user);
    }

//    public String login(String userId, String password) {
//        User user = userRepository.findByUserId(userId);
//        if (user == null) {
//            throw new RuntimeException("해당 사용자 정보가 존재하지 않습니다.");
//        }
//        if (!user.getPassword().equals(password)) {
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
//        return userId;
//    }

    // 아이디 중복 체크
    public boolean isUserIdExists(String userId) {
        User user = userRepository.findByUserId(userId); // 변수 이름 변경
        return user != null;
    }

    // 이메일 중복 체크
    public boolean isEmailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }


    //User 등록
//    public User save(AddUserRequest userRequest) {
//        return userRepository.save(userRequest.toEntity());
//    }

//    //User 전체 조회
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
//    //User id로 조회x`x`
//    public User findById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
//    }
//
//    //User 삭제
//    public void delete(Long id) {
//        userRepository.deleteById(id);
//    }
//
//    //User 수정
//   /* @Transactional
//    public User update(Long id, UpdatableUserRequest request) {
//        User user = UserRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: ") + id);
//
//        //article.update(request.getTitle(), request.getContent());
//        return user;
//    }*/

}