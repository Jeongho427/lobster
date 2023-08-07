package vacationproject.lobster.dto;

import lombok.Getter;
import vacationproject.lobster.domain.Calendar;
import vacationproject.lobster.domain.User;

@Getter
public class CalendarResponse {
    private String day_start;
    private String day_end;
    private String contents;
    private User calendarOwner;

    public CalendarResponse(Calendar calendar) {
        this.day_start = calendar.getDay_start();
        this.day_end = calendar.getDay_end();
        this.contents = calendar.getContents();
        this.calendarOwner = calendar.getCalendarOwner();
    }
}
