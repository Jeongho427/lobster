package vacationproject.lobster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vacationproject.lobster.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MembershipController {

    private final UserService userService;

    public MembershipController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 저장
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("user_name") String name,
                                           @RequestParam("user_id") String userId,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam("phone_num") String phoneNum,
                                           Model model) {

        userService.registerMembership(name, userId, email, password, phoneNum);
        return ResponseEntity.ok("회원 가입 성공");
    }

    // 아이디 중복 체크
    @PostMapping("/userIdExists")
    public ResponseEntity<String> checkUserIdExists(@RequestParam("user_id") String userId) {
        if (userService.isUserIdExists(userId)) {
            // 이미 존재하는 user_id인 경우
            return ResponseEntity.badRequest().body("User ID already exists.");
        } else {
            // 존재하지 않는 user_id인 경우
            return ResponseEntity.ok("User ID is available.");
        }
    }
}
