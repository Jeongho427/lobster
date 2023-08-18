package vacationproject.lobster.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateGroupRequest {
    private Long gId;
    private String groupName;
    private int memberCnt;
    private User creator;

}
