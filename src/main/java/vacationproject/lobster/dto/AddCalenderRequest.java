package vacationproject.lobster.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.Calender;
import vacationproject.lobster.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCalenderRequest {

    private String day_start;
    private String day_end;
    private String time_start;
    private String time_end;
    private String contents;
    private User calenderOwner;

    public Calender toEntity() {
        return Calender.builder()
                .day_start(day_start)
                .day_end(day_end)
                .time_start(time_start)
                .time_end(time_end)
                .contents(contents)
                .calenderOwner(calenderOwner)
                .build();
    }
}
