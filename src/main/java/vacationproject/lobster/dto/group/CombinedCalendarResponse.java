package vacationproject.lobster.dto.group;

import vacationproject.lobster.domain.Calender;

import java.util.List;

public class CombinedCalendarResponse {
    private List<Calender> combinedCalendars;

    public CombinedCalendarResponse(List<Calender> combinedCalendars) {
        this.combinedCalendars = combinedCalendars;
    }

    // Getter와 Setter 메서드 필요 시 추가
}
