package vacationproject.lobster.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InvitationService {
    private Map<String, String> invitationTokens = new HashMap<>();

    public String generateInvitationToken(long groupId, String userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        invitationTokens.put(token, userId + "_" + groupId); // 토큰과 유저 아이디, 그룹 아이디를 맵에 저장
        return token;
    }

    public String getUserIdAndGroupIdFromToken(String token) {
        return invitationTokens.get(token);
    }
}

