package vacationproject.lobster.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vacationproject.lobster.domain.Calender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCalenderRequest {

    private String day_start;
    private String day_end;
    private String contents;
    private String calenderOwner;

    public Calender toEntity() {
        return Calender.builder()
                .day_start(day_start)
                .day_end(day_end)
                .contents(contents)
                .calenderOwner(calenderOwner)
                .build();
    }
}
