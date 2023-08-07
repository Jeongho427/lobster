package vacationproject.lobster.dto;

import lombok.Getter;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;

@Getter
public class GroupResponse {

    private final String groupName;
    private final int memberCnt;
    private User creator;

    public GroupResponse(Group group) {
        this.groupName = group.getGroupName();
        this.memberCnt = group.getMemberCnt();
        this.creator = group.getCreator();
    }
}
