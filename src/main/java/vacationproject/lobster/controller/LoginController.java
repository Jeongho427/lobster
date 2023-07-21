package vacationproject.lobster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.LoginRequest;
import vacationproject.lobster.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if (checkLogin(request.getUserId(), request.getPassword())) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    // 로그인 계정 확인
    public boolean checkLogin(String userId, String password) {
        User user = userRepository.findByUserId(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}