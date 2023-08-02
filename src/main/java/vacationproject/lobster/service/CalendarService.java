package vacationproject.lobster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vacationproject.lobster.domain.Calendar;
import vacationproject.lobster.dto.AddCalendarRequest;
import vacationproject.lobster.dto.UpdateCalendarRequest;
import vacationproject.lobster.repository.CalendarRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    //Calender 생성
    public Calendar save(AddCalendarRequest calenderRequest) {
        return calendarRepository.save(calenderRequest.toEntity());
    }

    //Calender 전체 조회
    public List<Calendar> findAll() {
        return calendarRepository.findAll();
    }

    //Calender id로 조회
    public Calendar findById(Long id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //Calender 삭제
    public void delete(Long id) {
        calendarRepository.deleteById(id);
    }

    // Calender 수정 => 일정 추가 및 수정
    public Calendar update(Long id, UpdateCalendarRequest request) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        calendar.update(request.getDayStart(), request.getDayEnd(), request.getTimeStart(), request.getTimeEnd(),
                request.getContents());

        return calendar;
    }
}
