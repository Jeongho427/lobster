package vacationproject.lobster.dto;

import lombok.Getter;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;

@Getter
public class CalenderResponse {
    private String day_start;
    private String day_end;
    private String time_start;
    private String time_end;
    private String contents;
    private User calenderOwner;

    public CalenderResponse(Calender calender) {
        this.day_start = calender.getDay_start();
        this.day_end = calender.getDay_end();
        this.time_start = calender.getTime_start();
        this.time_end = calender.getTime_end();
        this.contents = calender.getContents();
        this.calenderOwner = calender.getCalenderOwner();
    }
}
