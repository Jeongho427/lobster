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
    private String day_start;
    private String day_end;
    private String contents;
    private boolean important;
    private User calenderOwner;
}
