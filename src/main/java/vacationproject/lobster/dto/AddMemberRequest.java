package vacationproject.lobster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMemberRequest {

    private Long groupId;
    private Long userId;
    private String color;

    public Member toEntity() {
        return Member.builder()
                .groupId(groupId)
                .userId(userId)
                .color(color)
                .build();
    }
}
