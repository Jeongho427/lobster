package vacationproject.lobster.dto.calender;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;

import java.util.Date;

@Getter
public class CalenderResponse {
    private Long cId;
    private Date day_start;
    private Date day_end;
    private String contents;
    private boolean important;
    @JsonBackReference
    private User calenderOwner;

    public CalenderResponse(Calender calender) {
        this.cId = calender.getCId();
        this.day_start = calender.getDay_start();
        this.day_end = calender.getDay_end();
        this.contents = calender.getContents();
        this.important = calender.isImportant();
        this.calenderOwner = calender.getCalenderOwner();
    }
}
