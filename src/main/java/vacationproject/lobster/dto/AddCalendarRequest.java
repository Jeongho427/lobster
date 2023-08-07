package vacationproject.lobster.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.Calendar;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCalendarRequest {

    private String day_start;
    private String day_end;
    private String contents;
    private User calendarOwner;

    public Calendar toEntity() {
        return Calendar.builder()
                .day_start(day_start)
                .day_end(day_end)
                .contents(contents)
                .calendarOwner(calendarOwner)
                .build();
    }
}
