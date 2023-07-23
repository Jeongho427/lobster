package vacationproject.lobster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddGroupRequest {

    private String groupName;
    private int memberCnt;
    private User creator;

    public Group toEntity() {
        return Group.builder()
                .groupName(groupName)
                .memberCnt(memberCnt)
                .creator(creator)
                .build();
    }
}
