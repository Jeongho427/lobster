package vacationproject.lobster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCalendarRequest {
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private String contents;
}
