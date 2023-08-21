package vacationproject.lobster.dto.user;

import lombok.Getter;
import lombok.Setter;
import vacationproject.lobster.domain.User;

import java.util.List;

@Getter
@Setter
public class GroupUsersResponse {
    private List<User> groupUsers;

    public GroupUsersResponse(List<User> groupUsers) {
        this.groupUsers = groupUsers;
    }
}
