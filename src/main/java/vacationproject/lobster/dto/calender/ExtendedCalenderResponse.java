package vacationproject.lobster.dto.calender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacationproject.lobster.domain.Group;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ExtendedCalenderResponse {
    private String userName;
    private List<Group> groups;
    private List<CalenderResponse> calenders;
}
