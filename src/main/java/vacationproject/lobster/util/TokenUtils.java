//package vacationproject.lobster.util;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import vacationproject.lobster.Config.JwtProperties;
//import vacationproject.lobster.domain.User;
//
//import java.time.Duration;
//import java.util.Date;
//
//@RequiredArgsConstructor
//@Service
//public class TokenUtils {
//
//    private final JwtProperties jwtProperties;
//    private final User user;
//
//    // 토큰 생성 메소드
//    public String generateToken(User user, Duration expiredAt) {
//        Date now = new Date();
//        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
//    }
//
//    private String makeToken(Date expiry, User user) {
//        Date now = new Date();
//
//        return Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setIssuer(jwtProperties.getIssuer())
//                .setIssuedAt(now)
//                .setExpiration(expiry)
//                .setSubject(user.getEmail())
//                .claim("id", user.getUserId()) // 유저 아이디를 클레임에 추가
//                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
//                .compact();
//    }
//
//    // 토큰 유효성 검사 메소드
//    public boolean validToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(jwtProperties.getSecretKey())
//                    .parseClaimsJws(token);
//
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // 토큰에서 유저 아이디 추출 메소드
//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtProperties.getSecretKey())
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.get("id", .class);
//    }
//}
