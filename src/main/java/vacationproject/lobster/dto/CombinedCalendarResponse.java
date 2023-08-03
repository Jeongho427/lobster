package vacationproject.lobster.dto;

import lombok.Getter;
import vacationproject.lobster.domain.Calendar;

import java.util.List;

@Getter
public class CombinedCalendarResponse {
    private List<Calendar> combinedCalendars;

    public CombinedCalendarResponse(List<Calendar> combinedCalendars) {
        this.combinedCalendars = combinedCalendars;
    }
}