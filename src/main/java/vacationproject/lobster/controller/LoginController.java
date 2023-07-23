package vacationproject.lobster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.LoginRequest;
import vacationproject.lobster.dto.NewPasswordRequest;
import vacationproject.lobster.repository.UserRepository;
import vacationproject.lobster.service.MailSenderService;
import vacationproject.lobster.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final UserService userService;

    public LoginController(UserRepository userRepository, UserService userService, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailSenderService = mailSenderService;
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
        return user != null && user.getPassword().equals(password);
    }

    // 비밀 번호 찾기 + 인증 코드 보내기
    @PostMapping("/passwordFind")
    public ResponseEntity<String> pwFind(@RequestBody LoginRequest request) {
        if(checkIdAndEmail(request.getUserId(), request.getEmail())) {
            String verificationCode = userService.generateVerificationCode();
            mailSenderService.run(request.getEmail(), verificationCode);

            return ResponseEntity.ok("인증번호 : " + verificationCode);
        } else {
            return ResponseEntity.badRequest().body("아이디와 이메일이 일치하지 않습니다.");
        }
    }

    // DB에서 아이디와 이메일 확인
    public boolean checkIdAndEmail(String userId, String email) {
        User user = userRepository.findByUserId(userId);
        User user_email = userRepository.findByEmail(email);

        return user != null && user.getEmail().equals(email);
    }

    @PostMapping("/newPassword")
    public ResponseEntity<String> newPassword(@RequestBody NewPasswordRequest request) {
        String userId = request.getUserId();
        String newPassword = request.getNewPassword();

        // 아이디에 해당하는 사용자를 데이터베이스에서 찾아옵니다.
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            user.setPassword(newPassword);

            userRepository.save(user);

            return ResponseEntity.ok("새로운 비밀번호가 설정되었습니다.");
        } else {
            // 아이디에 해당하는 사용자가 없는 경우 에러 메시지를 클라이언트에게 보냅니다.
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }
    }
}