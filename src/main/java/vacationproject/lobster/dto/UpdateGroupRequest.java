package vacationproject.lobster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateGroupRequest {
    private String groupName;
    private int memberCnt;
    private Long creator;

}
