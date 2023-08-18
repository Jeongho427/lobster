package vacationproject.lobster.dto.group;

import lombok.Getter;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;

@Getter
public class GroupResponse {

    private Long gId;
    private final String groupName;
    private final int memberCnt;
    private User creator;

    public GroupResponse(Group group) {
        this.gId = group.getGId();
        this.groupName = group.getGroupName();
        this.memberCnt = group.getMemberCnt();
        this.creator = group.getCreator();
    }
}
