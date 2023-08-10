package vacationproject.lobster.dto.calender;

import lombok.Getter;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;

@Getter
public class CalenderResponse {
    private String day_start;
    private String day_end;
    private String contents;
    private boolean important;
    private User calenderOwner;

    public CalenderResponse(Calender calender) {
        this.day_start = calender.getDay_start();
        this.day_end = calender.getDay_end();
        this.contents = calender.getContents();
        this.important = calender.isImportant();
        this.calenderOwner = calender.getCalenderOwner();
    }
}
