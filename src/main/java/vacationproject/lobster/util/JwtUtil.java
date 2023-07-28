//package vacationproject.lobster.util;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//public class JwtUtil {
//
//    // 보안적으로 안전한 키 생성
//    private static final String SECRET_KEY_BASE64 = "dGVhbUxvYnN0ZXIxOTk4dGVhbUxvYnN0ZXIxOTk4dGVhbUxvYnN0ZXIxOTk4";
//    private static final byte[] SECRET_KEY_BYTES = Base64.getDecoder().decode(SECRET_KEY_BASE64);
//    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_BYTES);
//
//    public static SecretKey getSecretKey() {
//        return SECRET_KEY;
//    }
//}
