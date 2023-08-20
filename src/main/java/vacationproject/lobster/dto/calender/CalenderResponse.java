package vacationproject.lobster.dto.calender;

import lombok.Getter;
import vacationproject.lobster.domain.Calender;
import java.util.Date;

@Getter
public class CalenderResponse {
    private Date day_start;
    private Date day_end;
    private String contents;
    private boolean important;

    public CalenderResponse(Calender calender) {
        this.day_start = calender.getDay_start();
        this.day_end = calender.getDay_end();
        this.contents = calender.getContents();
        this.important = calender.isImportant();
    }
}
