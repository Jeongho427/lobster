package vacationproject.lobster.dto;

import lombok.Getter;
import vacationproject.lobster.domain.Calender;

@Getter
public class CalenderResponse {
    private String day_start;
    private String day_end;
    private String contents;
    private String calenderOwner;

    public CalenderResponse(Calender calender) {
        this.day_start = calender.getDay_start();
        this.day_end = calender.getDay_end();
        this.contents = calender.getContents();
        this.calenderOwner = calender.getCalenderOwner();
    }
}
