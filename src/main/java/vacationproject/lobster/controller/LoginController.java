package vacationproject.lobster.controller;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vacationproject.lobster.Security.JwtProvider;
import vacationproject.lobster.domain.User;
import vacationproject.lobster.dto.LoginRequest;
import vacationproject.lobster.dto.NewPasswordRequest;
import vacationproject.lobster.dto.TokenDataResponse;
import vacationproject.lobster.repository.UserRepository;
import vacationproject.lobster.service.MailSenderService;
import vacationproject.lobster.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final JwtProvider jwtProvider;
    private final TokenDataResponse tokenDataResponse;



    public LoginController(
            UserService userService,
            MailSenderService mailSenderService,
            UserRepository userRepository,
            JwtProvider jwtProvider,
            TokenDataResponse tokenDataResponse) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.jwtProvider = jwtProvider;
        this.tokenDataResponse = tokenDataResponse;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String userId = request.getUserId();
        String password = request.getPassword();

        if (checkLogin(userId, password)) {
            // 로그인 성공 시, 토큰 생성
            User user = userRepository.findByUserId(userId);
            String token = jwtProvider.createToken(userId, user.getUserName());

            // 클라이언트에게 토큰을 반환
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    //==토큰 인증 컨트롤러==//
    @GetMapping(value = "/checkToken")
    public ResponseEntity<TokenDataResponse> checkToken(@RequestHeader(value = "Authorization") String token) {
        try {
            // Bearer 제거
            String parsedToken = token.substring("Bearer ".length());

            System.out.println(parsedToken);

            // 토큰의 유효성 검사 및 Claims 얻어오기
            Claims claims = jwtProvider.parseJwtToken(parsedToken);

            if (claims != null) {
                // 토큰이 유효한 경우 클라이언트에게 토큰 데이터를 응답
                String userId = claims.get("userId", String.class);
                String userName = claims.get("userName", String.class);
                TokenDataResponse tokenData = new TokenDataResponse(parsedToken, userId, userName, claims.getIssuedAt().toString(), claims.getExpiration().toString());
                return ResponseEntity.ok(tokenData);
            } else {
                // 토큰이 유효하지 않은 경우 클라이언트에게 에러 응답
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            // 토큰 파싱 또는 검증에 문제가 있는 경우 클라이언트에게 에러 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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

            // 인증 코드 생성 및 이메일 전송 등 로직 추가
            String thisIsEmail = request.getEmail();
            String verificationCode = userService.generateVerificationCode();
            mailSenderService.run(thisIsEmail, verificationCode);

            return ResponseEntity.ok(verificationCode);
        } else {
            return ResponseEntity.badRequest().body("아이디와 이메일이 일치하지 않습니다.");
        }
    }

    // DB에서 아이디와 이메일 확인
    public boolean checkIdAndEmail(String userId, String email) {
        User user = userRepository.findByUserId(userId);
        return user != null && user.getEmail().equals(email);
    }

    @PostMapping("/newPassword")
    public ResponseEntity<String> newPassword(@RequestBody NewPasswordRequest request) {
        String userId = request.getUserId();
        String newPassword = request.getNewPassword();

        User user = userRepository.findByUserId(userId);

        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok("새로운 비밀번호가 설정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }
    }
}