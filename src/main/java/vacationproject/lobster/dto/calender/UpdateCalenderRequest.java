package vacationproject.lobster.dto.calender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacationproject.lobster.domain.User;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UpdateCalenderRequest {
    private Date day_start;
    private Date day_end;
    private String contents;
    private boolean important;
    private User calenderOwner;
}
