package vacationproject.lobster.dto.group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacationproject.lobster.domain.Group;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AddGroupRequest {

    private String groupName;
    private int memberCnt;
    private User creator;

    public Group toEntity(User creator) {
        return Group.builder()
                .groupName(groupName)
                .memberCnt(memberCnt)
                .creator(creator)
                .build();
    }
}