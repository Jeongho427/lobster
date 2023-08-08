package vacationproject.lobster.dto.calender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UpdateCalenderRequest {
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private String contents;
    private User calenderOwner;
}
